package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Trade;
import com.agrobourse.dev.repository.TradeRepository;
import com.agrobourse.dev.repository.search.TradeSearchRepository;
import com.agrobourse.dev.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TradeResource REST controller.
 *
 * @see TradeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class TradeResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OF_ORDER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ORDER = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OF_DELIVERY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_DELIVERY = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRIX_UNITAIRE = 1D;
    private static final Double UPDATED_PRIX_UNITAIRE = 2D;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeSearchRepository tradeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTradeMockMvc;

    private Trade trade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TradeResource tradeResource = new TradeResource(tradeRepository, tradeSearchRepository);
        this.restTradeMockMvc = MockMvcBuilders.standaloneSetup(tradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trade createEntity(EntityManager em) {
        Trade trade = new Trade()
            .dateOfOrder(DEFAULT_DATE_OF_ORDER)
            .dateOfDelivery(DEFAULT_DATE_OF_DELIVERY)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .position(DEFAULT_POSITION)
            .quantity(DEFAULT_QUANTITY)
            .montant(DEFAULT_MONTANT)
            .status(DEFAULT_STATUS);
        return trade;
    }

    @Before
    public void initTest() {
        tradeSearchRepository.deleteAll();
        trade = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrade() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // Create the Trade
        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isCreated());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate + 1);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getDateOfOrder()).isEqualTo(DEFAULT_DATE_OF_ORDER);
        assertThat(testTrade.getDateOfDelivery()).isEqualTo(DEFAULT_DATE_OF_DELIVERY);
        assertThat(testTrade.getPrixUnitaire()).isEqualTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testTrade.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testTrade.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTrade.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTrade.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Trade in Elasticsearch
        Trade tradeEs = tradeSearchRepository.findOne(testTrade.getId());
        assertThat(tradeEs).isEqualToComparingFieldByField(testTrade);
    }

    @Test
    @Transactional
    public void createTradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // Create the Trade with an existing ID
        trade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrades() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        // Get all the tradeList
        restTradeMockMvc.perform(get("/api/trades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trade.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfOrder").value(hasItem(DEFAULT_DATE_OF_ORDER.toString())))
            .andExpect(jsonPath("$.[*].dateOfDelivery").value(hasItem(DEFAULT_DATE_OF_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        // Get the trade
        restTradeMockMvc.perform(get("/api/trades/{id}", trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trade.getId().intValue()))
            .andExpect(jsonPath("$.dateOfOrder").value(DEFAULT_DATE_OF_ORDER.toString()))
            .andExpect(jsonPath("$.dateOfDelivery").value(DEFAULT_DATE_OF_DELIVERY.toString()))
            .andExpect(jsonPath("$.prixUnitaire").value(DEFAULT_PRIX_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingTrade() throws Exception {
        // Get the trade
        restTradeMockMvc.perform(get("/api/trades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);
        tradeSearchRepository.save(trade);
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Update the trade
        Trade updatedTrade = tradeRepository.findOne(trade.getId());
        updatedTrade
            .dateOfOrder(UPDATED_DATE_OF_ORDER)
            .dateOfDelivery(UPDATED_DATE_OF_DELIVERY)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .position(UPDATED_POSITION)
            .quantity(UPDATED_QUANTITY)
            .montant(UPDATED_MONTANT)
            .status(UPDATED_STATUS);

        restTradeMockMvc.perform(put("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrade)))
            .andExpect(status().isOk());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getDateOfOrder()).isEqualTo(UPDATED_DATE_OF_ORDER);
        assertThat(testTrade.getDateOfDelivery()).isEqualTo(UPDATED_DATE_OF_DELIVERY);
        assertThat(testTrade.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testTrade.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testTrade.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTrade.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTrade.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Trade in Elasticsearch
        Trade tradeEs = tradeSearchRepository.findOne(testTrade.getId());
        assertThat(tradeEs).isEqualToComparingFieldByField(testTrade);
    }

    @Test
    @Transactional
    public void updateNonExistingTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Create the Trade

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTradeMockMvc.perform(put("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isCreated());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);
        tradeSearchRepository.save(trade);
        int databaseSizeBeforeDelete = tradeRepository.findAll().size();

        // Get the trade
        restTradeMockMvc.perform(delete("/api/trades/{id}", trade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tradeExistsInEs = tradeSearchRepository.exists(trade.getId());
        assertThat(tradeExistsInEs).isFalse();

        // Validate the database is empty
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);
        tradeSearchRepository.save(trade);

        // Search the trade
        restTradeMockMvc.perform(get("/api/_search/trades?query=id:" + trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trade.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfOrder").value(hasItem(DEFAULT_DATE_OF_ORDER.toString())))
            .andExpect(jsonPath("$.[*].dateOfDelivery").value(hasItem(DEFAULT_DATE_OF_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trade.class);
    }
}

package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.TransactionHistorique;
import com.agrobourse.dev.repository.TransactionHistoriqueRepository;
import com.agrobourse.dev.repository.search.TransactionHistoriqueSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.agrobourse.dev.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransactionHistoriqueResource REST controller.
 *
 * @see TransactionHistoriqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class TransactionHistoriqueResourceIntTest {

    private static final Integer DEFAULT_ETAT = 1;
    private static final Integer UPDATED_ETAT = 2;

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_NUMTRANSACTION = 1L;
    private static final Long UPDATED_NUMTRANSACTION = 2L;

    private static final Integer DEFAULT_CMDSPAYEES = 1;
    private static final Integer UPDATED_CMDSPAYEES = 2;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION_PAIMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION_PAIMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_REFUSE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REFUSE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_REFUSPAIMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REFUSPAIMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TransactionHistoriqueRepository transactionHistoriqueRepository;

    @Autowired
    private TransactionHistoriqueSearchRepository transactionHistoriqueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionHistoriqueMockMvc;

    private TransactionHistorique transactionHistorique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionHistoriqueResource transactionHistoriqueResource = new TransactionHistoriqueResource(transactionHistoriqueRepository, transactionHistoriqueSearchRepository);
        this.restTransactionHistoriqueMockMvc = MockMvcBuilders.standaloneSetup(transactionHistoriqueResource)
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
    public static TransactionHistorique createEntity(EntityManager em) {
        TransactionHistorique transactionHistorique = new TransactionHistorique()
            .etat(DEFAULT_ETAT)
            .prix(DEFAULT_PRIX)
            .date(DEFAULT_DATE)
            .numtransaction(DEFAULT_NUMTRANSACTION)
            .cmdspayees(DEFAULT_CMDSPAYEES)
            .image(DEFAULT_IMAGE)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .dateValidationPaiment(DEFAULT_DATE_VALIDATION_PAIMENT)
            .dateRefuse(DEFAULT_DATE_REFUSE)
            .dateRefuspaiment(DEFAULT_DATE_REFUSPAIMENT);
        return transactionHistorique;
    }

    @Before
    public void initTest() {
        transactionHistoriqueSearchRepository.deleteAll();
        transactionHistorique = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionHistorique() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoriqueRepository.findAll().size();

        // Create the TransactionHistorique
        restTransactionHistoriqueMockMvc.perform(post("/api/transaction-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistorique)))
            .andExpect(status().isCreated());

        // Validate the TransactionHistorique in the database
        List<TransactionHistorique> transactionHistoriqueList = transactionHistoriqueRepository.findAll();
        assertThat(transactionHistoriqueList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionHistorique testTransactionHistorique = transactionHistoriqueList.get(transactionHistoriqueList.size() - 1);
        assertThat(testTransactionHistorique.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testTransactionHistorique.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testTransactionHistorique.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTransactionHistorique.getNumtransaction()).isEqualTo(DEFAULT_NUMTRANSACTION);
        assertThat(testTransactionHistorique.getCmdspayees()).isEqualTo(DEFAULT_CMDSPAYEES);
        assertThat(testTransactionHistorique.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testTransactionHistorique.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testTransactionHistorique.getDateValidationPaiment()).isEqualTo(DEFAULT_DATE_VALIDATION_PAIMENT);
        assertThat(testTransactionHistorique.getDateRefuse()).isEqualTo(DEFAULT_DATE_REFUSE);
        assertThat(testTransactionHistorique.getDateRefuspaiment()).isEqualTo(DEFAULT_DATE_REFUSPAIMENT);

        // Validate the TransactionHistorique in Elasticsearch
        TransactionHistorique transactionHistoriqueEs = transactionHistoriqueSearchRepository.findOne(testTransactionHistorique.getId());
        assertThat(transactionHistoriqueEs).isEqualToComparingFieldByField(testTransactionHistorique);
    }

    @Test
    @Transactional
    public void createTransactionHistoriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoriqueRepository.findAll().size();

        // Create the TransactionHistorique with an existing ID
        transactionHistorique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionHistoriqueMockMvc.perform(post("/api/transaction-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistorique)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransactionHistorique> transactionHistoriqueList = transactionHistoriqueRepository.findAll();
        assertThat(transactionHistoriqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriques() throws Exception {
        // Initialize the database
        transactionHistoriqueRepository.saveAndFlush(transactionHistorique);

        // Get all the transactionHistoriqueList
        restTransactionHistoriqueMockMvc.perform(get("/api/transaction-historiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistorique.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numtransaction").value(hasItem(DEFAULT_NUMTRANSACTION.intValue())))
            .andExpect(jsonPath("$.[*].cmdspayees").value(hasItem(DEFAULT_CMDSPAYEES)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].dateValidationPaiment").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION_PAIMENT))))
            .andExpect(jsonPath("$.[*].dateRefuse").value(hasItem(sameInstant(DEFAULT_DATE_REFUSE))))
            .andExpect(jsonPath("$.[*].dateRefuspaiment").value(hasItem(sameInstant(DEFAULT_DATE_REFUSPAIMENT))));
    }

    @Test
    @Transactional
    public void getTransactionHistorique() throws Exception {
        // Initialize the database
        transactionHistoriqueRepository.saveAndFlush(transactionHistorique);

        // Get the transactionHistorique
        restTransactionHistoriqueMockMvc.perform(get("/api/transaction-historiques/{id}", transactionHistorique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionHistorique.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.numtransaction").value(DEFAULT_NUMTRANSACTION.intValue()))
            .andExpect(jsonPath("$.cmdspayees").value(DEFAULT_CMDSPAYEES))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.dateValidation").value(sameInstant(DEFAULT_DATE_VALIDATION)))
            .andExpect(jsonPath("$.dateValidationPaiment").value(sameInstant(DEFAULT_DATE_VALIDATION_PAIMENT)))
            .andExpect(jsonPath("$.dateRefuse").value(sameInstant(DEFAULT_DATE_REFUSE)))
            .andExpect(jsonPath("$.dateRefuspaiment").value(sameInstant(DEFAULT_DATE_REFUSPAIMENT)));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionHistorique() throws Exception {
        // Get the transactionHistorique
        restTransactionHistoriqueMockMvc.perform(get("/api/transaction-historiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionHistorique() throws Exception {
        // Initialize the database
        transactionHistoriqueRepository.saveAndFlush(transactionHistorique);
        transactionHistoriqueSearchRepository.save(transactionHistorique);
        int databaseSizeBeforeUpdate = transactionHistoriqueRepository.findAll().size();

        // Update the transactionHistorique
        TransactionHistorique updatedTransactionHistorique = transactionHistoriqueRepository.findOne(transactionHistorique.getId());
        updatedTransactionHistorique
            .etat(UPDATED_ETAT)
            .prix(UPDATED_PRIX)
            .date(UPDATED_DATE)
            .numtransaction(UPDATED_NUMTRANSACTION)
            .cmdspayees(UPDATED_CMDSPAYEES)
            .image(UPDATED_IMAGE)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .dateValidationPaiment(UPDATED_DATE_VALIDATION_PAIMENT)
            .dateRefuse(UPDATED_DATE_REFUSE)
            .dateRefuspaiment(UPDATED_DATE_REFUSPAIMENT);

        restTransactionHistoriqueMockMvc.perform(put("/api/transaction-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionHistorique)))
            .andExpect(status().isOk());

        // Validate the TransactionHistorique in the database
        List<TransactionHistorique> transactionHistoriqueList = transactionHistoriqueRepository.findAll();
        assertThat(transactionHistoriqueList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistorique testTransactionHistorique = transactionHistoriqueList.get(transactionHistoriqueList.size() - 1);
        assertThat(testTransactionHistorique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testTransactionHistorique.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testTransactionHistorique.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransactionHistorique.getNumtransaction()).isEqualTo(UPDATED_NUMTRANSACTION);
        assertThat(testTransactionHistorique.getCmdspayees()).isEqualTo(UPDATED_CMDSPAYEES);
        assertThat(testTransactionHistorique.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testTransactionHistorique.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testTransactionHistorique.getDateValidationPaiment()).isEqualTo(UPDATED_DATE_VALIDATION_PAIMENT);
        assertThat(testTransactionHistorique.getDateRefuse()).isEqualTo(UPDATED_DATE_REFUSE);
        assertThat(testTransactionHistorique.getDateRefuspaiment()).isEqualTo(UPDATED_DATE_REFUSPAIMENT);

        // Validate the TransactionHistorique in Elasticsearch
        TransactionHistorique transactionHistoriqueEs = transactionHistoriqueSearchRepository.findOne(testTransactionHistorique.getId());
        assertThat(transactionHistoriqueEs).isEqualToComparingFieldByField(testTransactionHistorique);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionHistorique() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoriqueRepository.findAll().size();

        // Create the TransactionHistorique

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionHistoriqueMockMvc.perform(put("/api/transaction-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistorique)))
            .andExpect(status().isCreated());

        // Validate the TransactionHistorique in the database
        List<TransactionHistorique> transactionHistoriqueList = transactionHistoriqueRepository.findAll();
        assertThat(transactionHistoriqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionHistorique() throws Exception {
        // Initialize the database
        transactionHistoriqueRepository.saveAndFlush(transactionHistorique);
        transactionHistoriqueSearchRepository.save(transactionHistorique);
        int databaseSizeBeforeDelete = transactionHistoriqueRepository.findAll().size();

        // Get the transactionHistorique
        restTransactionHistoriqueMockMvc.perform(delete("/api/transaction-historiques/{id}", transactionHistorique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transactionHistoriqueExistsInEs = transactionHistoriqueSearchRepository.exists(transactionHistorique.getId());
        assertThat(transactionHistoriqueExistsInEs).isFalse();

        // Validate the database is empty
        List<TransactionHistorique> transactionHistoriqueList = transactionHistoriqueRepository.findAll();
        assertThat(transactionHistoriqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransactionHistorique() throws Exception {
        // Initialize the database
        transactionHistoriqueRepository.saveAndFlush(transactionHistorique);
        transactionHistoriqueSearchRepository.save(transactionHistorique);

        // Search the transactionHistorique
        restTransactionHistoriqueMockMvc.perform(get("/api/_search/transaction-historiques?query=id:" + transactionHistorique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistorique.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numtransaction").value(hasItem(DEFAULT_NUMTRANSACTION.intValue())))
            .andExpect(jsonPath("$.[*].cmdspayees").value(hasItem(DEFAULT_CMDSPAYEES)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].dateValidationPaiment").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION_PAIMENT))))
            .andExpect(jsonPath("$.[*].dateRefuse").value(hasItem(sameInstant(DEFAULT_DATE_REFUSE))))
            .andExpect(jsonPath("$.[*].dateRefuspaiment").value(hasItem(sameInstant(DEFAULT_DATE_REFUSPAIMENT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionHistorique.class);
    }
}

package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Production;
import com.agrobourse.dev.repository.ProductionRepository;
import com.agrobourse.dev.repository.search.ProductionSearchRepository;
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
 * Test class for the ProductionResource REST controller.
 *
 * @see ProductionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class ProductionResourceIntTest {

    private static final LocalDate DEFAULT_DATEOFPRODUCTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEOFPRODUCTION = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ProductionSearchRepository productionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductionMockMvc;

    private Production production;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductionResource productionResource = new ProductionResource(productionRepository, productionSearchRepository);
        this.restProductionMockMvc = MockMvcBuilders.standaloneSetup(productionResource)
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
    public static Production createEntity(EntityManager em) {
        Production production = new Production()
            .dateofproduction(DEFAULT_DATEOFPRODUCTION)
            .quantity(DEFAULT_QUANTITY);
        return production;
    }

    @Before
    public void initTest() {
        productionSearchRepository.deleteAll();
        production = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduction() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isCreated());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate + 1);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getDateofproduction()).isEqualTo(DEFAULT_DATEOFPRODUCTION);
        assertThat(testProduction.getQuantity()).isEqualTo(DEFAULT_QUANTITY);

        // Validate the Production in Elasticsearch
        Production productionEs = productionSearchRepository.findOne(testProduction.getId());
        assertThat(productionEs).isEqualToComparingFieldByField(testProduction);
    }

    @Test
    @Transactional
    public void createProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production with an existing ID
        production.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductions() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList
        restProductionMockMvc.perform(get("/api/productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateofproduction").value(hasItem(DEFAULT_DATEOFPRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(production.getId().intValue()))
            .andExpect(jsonPath("$.dateofproduction").value(DEFAULT_DATEOFPRODUCTION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduction() throws Exception {
        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);
        productionSearchRepository.save(production);
        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Update the production
        Production updatedProduction = productionRepository.findOne(production.getId());
        updatedProduction
            .dateofproduction(UPDATED_DATEOFPRODUCTION)
            .quantity(UPDATED_QUANTITY);

        restProductionMockMvc.perform(put("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduction)))
            .andExpect(status().isOk());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getDateofproduction()).isEqualTo(UPDATED_DATEOFPRODUCTION);
        assertThat(testProduction.getQuantity()).isEqualTo(UPDATED_QUANTITY);

        // Validate the Production in Elasticsearch
        Production productionEs = productionSearchRepository.findOne(testProduction.getId());
        assertThat(productionEs).isEqualToComparingFieldByField(testProduction);
    }

    @Test
    @Transactional
    public void updateNonExistingProduction() throws Exception {
        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Create the Production

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductionMockMvc.perform(put("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isCreated());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);
        productionSearchRepository.save(production);
        int databaseSizeBeforeDelete = productionRepository.findAll().size();

        // Get the production
        restProductionMockMvc.perform(delete("/api/productions/{id}", production.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productionExistsInEs = productionSearchRepository.exists(production.getId());
        assertThat(productionExistsInEs).isFalse();

        // Validate the database is empty
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);
        productionSearchRepository.save(production);

        // Search the production
        restProductionMockMvc.perform(get("/api/_search/productions?query=id:" + production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateofproduction").value(hasItem(DEFAULT_DATEOFPRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Production.class);
    }
}

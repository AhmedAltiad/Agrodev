package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.TraderCA;
import com.agrobourse.dev.repository.TraderCARepository;
import com.agrobourse.dev.repository.search.TraderCASearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TraderCAResource REST controller.
 *
 * @see TraderCAResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class TraderCAResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private TraderCARepository traderCARepository;

    @Autowired
    private TraderCASearchRepository traderCASearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraderCAMockMvc;

    private TraderCA traderCA;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraderCAResource traderCAResource = new TraderCAResource(traderCARepository, traderCASearchRepository);
        this.restTraderCAMockMvc = MockMvcBuilders.standaloneSetup(traderCAResource)
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
    public static TraderCA createEntity(EntityManager em) {
        TraderCA traderCA = new TraderCA()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .adresse(DEFAULT_ADRESSE);
        return traderCA;
    }

    @Before
    public void initTest() {
        traderCASearchRepository.deleteAll();
        traderCA = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraderCA() throws Exception {
        int databaseSizeBeforeCreate = traderCARepository.findAll().size();

        // Create the TraderCA
        restTraderCAMockMvc.perform(post("/api/trader-cas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traderCA)))
            .andExpect(status().isCreated());

        // Validate the TraderCA in the database
        List<TraderCA> traderCAList = traderCARepository.findAll();
        assertThat(traderCAList).hasSize(databaseSizeBeforeCreate + 1);
        TraderCA testTraderCA = traderCAList.get(traderCAList.size() - 1);
        assertThat(testTraderCA.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTraderCA.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTraderCA.getAdresse()).isEqualTo(DEFAULT_ADRESSE);

        // Validate the TraderCA in Elasticsearch
        TraderCA traderCAEs = traderCASearchRepository.findOne(testTraderCA.getId());
        assertThat(traderCAEs).isEqualToComparingFieldByField(testTraderCA);
    }

    @Test
    @Transactional
    public void createTraderCAWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traderCARepository.findAll().size();

        // Create the TraderCA with an existing ID
        traderCA.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraderCAMockMvc.perform(post("/api/trader-cas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traderCA)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraderCA> traderCAList = traderCARepository.findAll();
        assertThat(traderCAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraderCAS() throws Exception {
        // Initialize the database
        traderCARepository.saveAndFlush(traderCA);

        // Get all the traderCAList
        restTraderCAMockMvc.perform(get("/api/trader-cas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traderCA.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getTraderCA() throws Exception {
        // Initialize the database
        traderCARepository.saveAndFlush(traderCA);

        // Get the traderCA
        restTraderCAMockMvc.perform(get("/api/trader-cas/{id}", traderCA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traderCA.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraderCA() throws Exception {
        // Get the traderCA
        restTraderCAMockMvc.perform(get("/api/trader-cas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraderCA() throws Exception {
        // Initialize the database
        traderCARepository.saveAndFlush(traderCA);
        traderCASearchRepository.save(traderCA);
        int databaseSizeBeforeUpdate = traderCARepository.findAll().size();

        // Update the traderCA
        TraderCA updatedTraderCA = traderCARepository.findOne(traderCA.getId());
        updatedTraderCA
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .adresse(UPDATED_ADRESSE);

        restTraderCAMockMvc.perform(put("/api/trader-cas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraderCA)))
            .andExpect(status().isOk());

        // Validate the TraderCA in the database
        List<TraderCA> traderCAList = traderCARepository.findAll();
        assertThat(traderCAList).hasSize(databaseSizeBeforeUpdate);
        TraderCA testTraderCA = traderCAList.get(traderCAList.size() - 1);
        assertThat(testTraderCA.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTraderCA.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTraderCA.getAdresse()).isEqualTo(UPDATED_ADRESSE);

        // Validate the TraderCA in Elasticsearch
        TraderCA traderCAEs = traderCASearchRepository.findOne(testTraderCA.getId());
        assertThat(traderCAEs).isEqualToComparingFieldByField(testTraderCA);
    }

    @Test
    @Transactional
    public void updateNonExistingTraderCA() throws Exception {
        int databaseSizeBeforeUpdate = traderCARepository.findAll().size();

        // Create the TraderCA

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraderCAMockMvc.perform(put("/api/trader-cas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traderCA)))
            .andExpect(status().isCreated());

        // Validate the TraderCA in the database
        List<TraderCA> traderCAList = traderCARepository.findAll();
        assertThat(traderCAList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraderCA() throws Exception {
        // Initialize the database
        traderCARepository.saveAndFlush(traderCA);
        traderCASearchRepository.save(traderCA);
        int databaseSizeBeforeDelete = traderCARepository.findAll().size();

        // Get the traderCA
        restTraderCAMockMvc.perform(delete("/api/trader-cas/{id}", traderCA.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean traderCAExistsInEs = traderCASearchRepository.exists(traderCA.getId());
        assertThat(traderCAExistsInEs).isFalse();

        // Validate the database is empty
        List<TraderCA> traderCAList = traderCARepository.findAll();
        assertThat(traderCAList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTraderCA() throws Exception {
        // Initialize the database
        traderCARepository.saveAndFlush(traderCA);
        traderCASearchRepository.save(traderCA);

        // Search the traderCA
        restTraderCAMockMvc.perform(get("/api/_search/trader-cas?query=id:" + traderCA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traderCA.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraderCA.class);
    }
}

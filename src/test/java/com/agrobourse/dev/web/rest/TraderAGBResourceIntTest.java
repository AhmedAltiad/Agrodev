package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.TraderAGB;
import com.agrobourse.dev.repository.TraderAGBRepository;
import com.agrobourse.dev.repository.search.TraderAGBSearchRepository;
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
 * Test class for the TraderAGBResource REST controller.
 *
 * @see TraderAGBResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class TraderAGBResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private TraderAGBRepository traderAGBRepository;

    @Autowired
    private TraderAGBSearchRepository traderAGBSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraderAGBMockMvc;

    private TraderAGB traderAGB;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraderAGBResource traderAGBResource = new TraderAGBResource(traderAGBRepository, traderAGBSearchRepository);
        this.restTraderAGBMockMvc = MockMvcBuilders.standaloneSetup(traderAGBResource)
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
    public static TraderAGB createEntity(EntityManager em) {
        TraderAGB traderAGB = new TraderAGB()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .adresse(DEFAULT_ADRESSE);
        return traderAGB;
    }

    @Before
    public void initTest() {
        traderAGBSearchRepository.deleteAll();
        traderAGB = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraderAGB() throws Exception {
        int databaseSizeBeforeCreate = traderAGBRepository.findAll().size();

        // Create the TraderAGB
        restTraderAGBMockMvc.perform(post("/api/trader-agbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traderAGB)))
            .andExpect(status().isCreated());

        // Validate the TraderAGB in the database
        List<TraderAGB> traderAGBList = traderAGBRepository.findAll();
        assertThat(traderAGBList).hasSize(databaseSizeBeforeCreate + 1);
        TraderAGB testTraderAGB = traderAGBList.get(traderAGBList.size() - 1);
        assertThat(testTraderAGB.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTraderAGB.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTraderAGB.getAdresse()).isEqualTo(DEFAULT_ADRESSE);

        // Validate the TraderAGB in Elasticsearch
        TraderAGB traderAGBEs = traderAGBSearchRepository.findOne(testTraderAGB.getId());
        assertThat(traderAGBEs).isEqualToComparingFieldByField(testTraderAGB);
    }

    @Test
    @Transactional
    public void createTraderAGBWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traderAGBRepository.findAll().size();

        // Create the TraderAGB with an existing ID
        traderAGB.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraderAGBMockMvc.perform(post("/api/trader-agbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traderAGB)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraderAGB> traderAGBList = traderAGBRepository.findAll();
        assertThat(traderAGBList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraderAGBS() throws Exception {
        // Initialize the database
        traderAGBRepository.saveAndFlush(traderAGB);

        // Get all the traderAGBList
        restTraderAGBMockMvc.perform(get("/api/trader-agbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traderAGB.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getTraderAGB() throws Exception {
        // Initialize the database
        traderAGBRepository.saveAndFlush(traderAGB);

        // Get the traderAGB
        restTraderAGBMockMvc.perform(get("/api/trader-agbs/{id}", traderAGB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traderAGB.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraderAGB() throws Exception {
        // Get the traderAGB
        restTraderAGBMockMvc.perform(get("/api/trader-agbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraderAGB() throws Exception {
        // Initialize the database
        traderAGBRepository.saveAndFlush(traderAGB);
        traderAGBSearchRepository.save(traderAGB);
        int databaseSizeBeforeUpdate = traderAGBRepository.findAll().size();

        // Update the traderAGB
        TraderAGB updatedTraderAGB = traderAGBRepository.findOne(traderAGB.getId());
        updatedTraderAGB
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .adresse(UPDATED_ADRESSE);

        restTraderAGBMockMvc.perform(put("/api/trader-agbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraderAGB)))
            .andExpect(status().isOk());

        // Validate the TraderAGB in the database
        List<TraderAGB> traderAGBList = traderAGBRepository.findAll();
        assertThat(traderAGBList).hasSize(databaseSizeBeforeUpdate);
        TraderAGB testTraderAGB = traderAGBList.get(traderAGBList.size() - 1);
        assertThat(testTraderAGB.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTraderAGB.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTraderAGB.getAdresse()).isEqualTo(UPDATED_ADRESSE);

        // Validate the TraderAGB in Elasticsearch
        TraderAGB traderAGBEs = traderAGBSearchRepository.findOne(testTraderAGB.getId());
        assertThat(traderAGBEs).isEqualToComparingFieldByField(testTraderAGB);
    }

    @Test
    @Transactional
    public void updateNonExistingTraderAGB() throws Exception {
        int databaseSizeBeforeUpdate = traderAGBRepository.findAll().size();

        // Create the TraderAGB

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraderAGBMockMvc.perform(put("/api/trader-agbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traderAGB)))
            .andExpect(status().isCreated());

        // Validate the TraderAGB in the database
        List<TraderAGB> traderAGBList = traderAGBRepository.findAll();
        assertThat(traderAGBList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraderAGB() throws Exception {
        // Initialize the database
        traderAGBRepository.saveAndFlush(traderAGB);
        traderAGBSearchRepository.save(traderAGB);
        int databaseSizeBeforeDelete = traderAGBRepository.findAll().size();

        // Get the traderAGB
        restTraderAGBMockMvc.perform(delete("/api/trader-agbs/{id}", traderAGB.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean traderAGBExistsInEs = traderAGBSearchRepository.exists(traderAGB.getId());
        assertThat(traderAGBExistsInEs).isFalse();

        // Validate the database is empty
        List<TraderAGB> traderAGBList = traderAGBRepository.findAll();
        assertThat(traderAGBList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTraderAGB() throws Exception {
        // Initialize the database
        traderAGBRepository.saveAndFlush(traderAGB);
        traderAGBSearchRepository.save(traderAGB);

        // Search the traderAGB
        restTraderAGBMockMvc.perform(get("/api/_search/trader-agbs?query=id:" + traderAGB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traderAGB.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraderAGB.class);
    }
}

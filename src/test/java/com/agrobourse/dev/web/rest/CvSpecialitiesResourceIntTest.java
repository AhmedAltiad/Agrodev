package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.CvSpecialities;
import com.agrobourse.dev.repository.CvSpecialitiesRepository;
import com.agrobourse.dev.repository.search.CvSpecialitiesSearchRepository;
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
 * Test class for the CvSpecialitiesResource REST controller.
 *
 * @see CvSpecialitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class CvSpecialitiesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CvSpecialitiesRepository cvSpecialitiesRepository;

    @Autowired
    private CvSpecialitiesSearchRepository cvSpecialitiesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCvSpecialitiesMockMvc;

    private CvSpecialities cvSpecialities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CvSpecialitiesResource cvSpecialitiesResource = new CvSpecialitiesResource(cvSpecialitiesRepository, cvSpecialitiesSearchRepository);
        this.restCvSpecialitiesMockMvc = MockMvcBuilders.standaloneSetup(cvSpecialitiesResource)
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
    public static CvSpecialities createEntity(EntityManager em) {
        CvSpecialities cvSpecialities = new CvSpecialities()
            .name(DEFAULT_NAME);
        return cvSpecialities;
    }

    @Before
    public void initTest() {
        cvSpecialitiesSearchRepository.deleteAll();
        cvSpecialities = createEntity(em);
    }

    @Test
    @Transactional
    public void createCvSpecialities() throws Exception {
        int databaseSizeBeforeCreate = cvSpecialitiesRepository.findAll().size();

        // Create the CvSpecialities
        restCvSpecialitiesMockMvc.perform(post("/api/cv-specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cvSpecialities)))
            .andExpect(status().isCreated());

        // Validate the CvSpecialities in the database
        List<CvSpecialities> cvSpecialitiesList = cvSpecialitiesRepository.findAll();
        assertThat(cvSpecialitiesList).hasSize(databaseSizeBeforeCreate + 1);
        CvSpecialities testCvSpecialities = cvSpecialitiesList.get(cvSpecialitiesList.size() - 1);
        assertThat(testCvSpecialities.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the CvSpecialities in Elasticsearch
        CvSpecialities cvSpecialitiesEs = cvSpecialitiesSearchRepository.findOne(testCvSpecialities.getId());
        assertThat(cvSpecialitiesEs).isEqualToComparingFieldByField(testCvSpecialities);
    }

    @Test
    @Transactional
    public void createCvSpecialitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cvSpecialitiesRepository.findAll().size();

        // Create the CvSpecialities with an existing ID
        cvSpecialities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCvSpecialitiesMockMvc.perform(post("/api/cv-specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cvSpecialities)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CvSpecialities> cvSpecialitiesList = cvSpecialitiesRepository.findAll();
        assertThat(cvSpecialitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCvSpecialities() throws Exception {
        // Initialize the database
        cvSpecialitiesRepository.saveAndFlush(cvSpecialities);

        // Get all the cvSpecialitiesList
        restCvSpecialitiesMockMvc.perform(get("/api/cv-specialities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cvSpecialities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCvSpecialities() throws Exception {
        // Initialize the database
        cvSpecialitiesRepository.saveAndFlush(cvSpecialities);

        // Get the cvSpecialities
        restCvSpecialitiesMockMvc.perform(get("/api/cv-specialities/{id}", cvSpecialities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cvSpecialities.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCvSpecialities() throws Exception {
        // Get the cvSpecialities
        restCvSpecialitiesMockMvc.perform(get("/api/cv-specialities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCvSpecialities() throws Exception {
        // Initialize the database
        cvSpecialitiesRepository.saveAndFlush(cvSpecialities);
        cvSpecialitiesSearchRepository.save(cvSpecialities);
        int databaseSizeBeforeUpdate = cvSpecialitiesRepository.findAll().size();

        // Update the cvSpecialities
        CvSpecialities updatedCvSpecialities = cvSpecialitiesRepository.findOne(cvSpecialities.getId());
        updatedCvSpecialities
            .name(UPDATED_NAME);

        restCvSpecialitiesMockMvc.perform(put("/api/cv-specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCvSpecialities)))
            .andExpect(status().isOk());

        // Validate the CvSpecialities in the database
        List<CvSpecialities> cvSpecialitiesList = cvSpecialitiesRepository.findAll();
        assertThat(cvSpecialitiesList).hasSize(databaseSizeBeforeUpdate);
        CvSpecialities testCvSpecialities = cvSpecialitiesList.get(cvSpecialitiesList.size() - 1);
        assertThat(testCvSpecialities.getName()).isEqualTo(UPDATED_NAME);

        // Validate the CvSpecialities in Elasticsearch
        CvSpecialities cvSpecialitiesEs = cvSpecialitiesSearchRepository.findOne(testCvSpecialities.getId());
        assertThat(cvSpecialitiesEs).isEqualToComparingFieldByField(testCvSpecialities);
    }

    @Test
    @Transactional
    public void updateNonExistingCvSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = cvSpecialitiesRepository.findAll().size();

        // Create the CvSpecialities

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCvSpecialitiesMockMvc.perform(put("/api/cv-specialities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cvSpecialities)))
            .andExpect(status().isCreated());

        // Validate the CvSpecialities in the database
        List<CvSpecialities> cvSpecialitiesList = cvSpecialitiesRepository.findAll();
        assertThat(cvSpecialitiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCvSpecialities() throws Exception {
        // Initialize the database
        cvSpecialitiesRepository.saveAndFlush(cvSpecialities);
        cvSpecialitiesSearchRepository.save(cvSpecialities);
        int databaseSizeBeforeDelete = cvSpecialitiesRepository.findAll().size();

        // Get the cvSpecialities
        restCvSpecialitiesMockMvc.perform(delete("/api/cv-specialities/{id}", cvSpecialities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cvSpecialitiesExistsInEs = cvSpecialitiesSearchRepository.exists(cvSpecialities.getId());
        assertThat(cvSpecialitiesExistsInEs).isFalse();

        // Validate the database is empty
        List<CvSpecialities> cvSpecialitiesList = cvSpecialitiesRepository.findAll();
        assertThat(cvSpecialitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCvSpecialities() throws Exception {
        // Initialize the database
        cvSpecialitiesRepository.saveAndFlush(cvSpecialities);
        cvSpecialitiesSearchRepository.save(cvSpecialities);

        // Search the cvSpecialities
        restCvSpecialitiesMockMvc.perform(get("/api/_search/cv-specialities?query=id:" + cvSpecialities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cvSpecialities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CvSpecialities.class);
    }
}

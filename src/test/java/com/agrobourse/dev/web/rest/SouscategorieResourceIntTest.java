package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Souscategorie;
import com.agrobourse.dev.repository.SouscategorieRepository;
import com.agrobourse.dev.repository.search.SouscategorieSearchRepository;
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
 * Test class for the SouscategorieResource REST controller.
 *
 * @see SouscategorieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class SouscategorieResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SouscategorieRepository souscategorieRepository;

    @Autowired
    private SouscategorieSearchRepository souscategorieSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSouscategorieMockMvc;

    private Souscategorie souscategorie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SouscategorieResource souscategorieResource = new SouscategorieResource(souscategorieRepository, souscategorieSearchRepository);
        this.restSouscategorieMockMvc = MockMvcBuilders.standaloneSetup(souscategorieResource)
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
    public static Souscategorie createEntity(EntityManager em) {
        Souscategorie souscategorie = new Souscategorie()
            .name(DEFAULT_NAME);
        return souscategorie;
    }

    @Before
    public void initTest() {
        souscategorieSearchRepository.deleteAll();
        souscategorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSouscategorie() throws Exception {
        int databaseSizeBeforeCreate = souscategorieRepository.findAll().size();

        // Create the Souscategorie
        restSouscategorieMockMvc.perform(post("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isCreated());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeCreate + 1);
        Souscategorie testSouscategorie = souscategorieList.get(souscategorieList.size() - 1);
        assertThat(testSouscategorie.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Souscategorie in Elasticsearch
        Souscategorie souscategorieEs = souscategorieSearchRepository.findOne(testSouscategorie.getId());
        assertThat(souscategorieEs).isEqualToComparingFieldByField(testSouscategorie);
    }

    @Test
    @Transactional
    public void createSouscategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = souscategorieRepository.findAll().size();

        // Create the Souscategorie with an existing ID
        souscategorie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSouscategorieMockMvc.perform(post("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSouscategories() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);

        // Get all the souscategorieList
        restSouscategorieMockMvc.perform(get("/api/souscategories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(souscategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);

        // Get the souscategorie
        restSouscategorieMockMvc.perform(get("/api/souscategories/{id}", souscategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(souscategorie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSouscategorie() throws Exception {
        // Get the souscategorie
        restSouscategorieMockMvc.perform(get("/api/souscategories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);
        souscategorieSearchRepository.save(souscategorie);
        int databaseSizeBeforeUpdate = souscategorieRepository.findAll().size();

        // Update the souscategorie
        Souscategorie updatedSouscategorie = souscategorieRepository.findOne(souscategorie.getId());
        updatedSouscategorie
            .name(UPDATED_NAME);

        restSouscategorieMockMvc.perform(put("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSouscategorie)))
            .andExpect(status().isOk());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeUpdate);
        Souscategorie testSouscategorie = souscategorieList.get(souscategorieList.size() - 1);
        assertThat(testSouscategorie.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Souscategorie in Elasticsearch
        Souscategorie souscategorieEs = souscategorieSearchRepository.findOne(testSouscategorie.getId());
        assertThat(souscategorieEs).isEqualToComparingFieldByField(testSouscategorie);
    }

    @Test
    @Transactional
    public void updateNonExistingSouscategorie() throws Exception {
        int databaseSizeBeforeUpdate = souscategorieRepository.findAll().size();

        // Create the Souscategorie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSouscategorieMockMvc.perform(put("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isCreated());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);
        souscategorieSearchRepository.save(souscategorie);
        int databaseSizeBeforeDelete = souscategorieRepository.findAll().size();

        // Get the souscategorie
        restSouscategorieMockMvc.perform(delete("/api/souscategories/{id}", souscategorie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean souscategorieExistsInEs = souscategorieSearchRepository.exists(souscategorie.getId());
        assertThat(souscategorieExistsInEs).isFalse();

        // Validate the database is empty
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);
        souscategorieSearchRepository.save(souscategorie);

        // Search the souscategorie
        restSouscategorieMockMvc.perform(get("/api/_search/souscategories?query=id:" + souscategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(souscategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Souscategorie.class);
    }
}

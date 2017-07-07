package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Gouvernorat;
import com.agrobourse.dev.repository.GouvernoratRepository;
import com.agrobourse.dev.repository.search.GouvernoratSearchRepository;
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
 * Test class for the GouvernoratResource REST controller.
 *
 * @see GouvernoratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class GouvernoratResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GouvernoratRepository gouvernoratRepository;

    @Autowired
    private GouvernoratSearchRepository gouvernoratSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGouvernoratMockMvc;

    private Gouvernorat gouvernorat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GouvernoratResource gouvernoratResource = new GouvernoratResource(gouvernoratRepository, gouvernoratSearchRepository);
        this.restGouvernoratMockMvc = MockMvcBuilders.standaloneSetup(gouvernoratResource)
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
    public static Gouvernorat createEntity(EntityManager em) {
        Gouvernorat gouvernorat = new Gouvernorat()
            .name(DEFAULT_NAME);
        return gouvernorat;
    }

    @Before
    public void initTest() {
        gouvernoratSearchRepository.deleteAll();
        gouvernorat = createEntity(em);
    }

    @Test
    @Transactional
    public void createGouvernorat() throws Exception {
        int databaseSizeBeforeCreate = gouvernoratRepository.findAll().size();

        // Create the Gouvernorat
        restGouvernoratMockMvc.perform(post("/api/gouvernorats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gouvernorat)))
            .andExpect(status().isCreated());

        // Validate the Gouvernorat in the database
        List<Gouvernorat> gouvernoratList = gouvernoratRepository.findAll();
        assertThat(gouvernoratList).hasSize(databaseSizeBeforeCreate + 1);
        Gouvernorat testGouvernorat = gouvernoratList.get(gouvernoratList.size() - 1);
        assertThat(testGouvernorat.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Gouvernorat in Elasticsearch
        Gouvernorat gouvernoratEs = gouvernoratSearchRepository.findOne(testGouvernorat.getId());
        assertThat(gouvernoratEs).isEqualToComparingFieldByField(testGouvernorat);
    }

    @Test
    @Transactional
    public void createGouvernoratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gouvernoratRepository.findAll().size();

        // Create the Gouvernorat with an existing ID
        gouvernorat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGouvernoratMockMvc.perform(post("/api/gouvernorats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gouvernorat)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Gouvernorat> gouvernoratList = gouvernoratRepository.findAll();
        assertThat(gouvernoratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGouvernorats() throws Exception {
        // Initialize the database
        gouvernoratRepository.saveAndFlush(gouvernorat);

        // Get all the gouvernoratList
        restGouvernoratMockMvc.perform(get("/api/gouvernorats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gouvernorat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGouvernorat() throws Exception {
        // Initialize the database
        gouvernoratRepository.saveAndFlush(gouvernorat);

        // Get the gouvernorat
        restGouvernoratMockMvc.perform(get("/api/gouvernorats/{id}", gouvernorat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gouvernorat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGouvernorat() throws Exception {
        // Get the gouvernorat
        restGouvernoratMockMvc.perform(get("/api/gouvernorats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGouvernorat() throws Exception {
        // Initialize the database
        gouvernoratRepository.saveAndFlush(gouvernorat);
        gouvernoratSearchRepository.save(gouvernorat);
        int databaseSizeBeforeUpdate = gouvernoratRepository.findAll().size();

        // Update the gouvernorat
        Gouvernorat updatedGouvernorat = gouvernoratRepository.findOne(gouvernorat.getId());
        updatedGouvernorat
            .name(UPDATED_NAME);

        restGouvernoratMockMvc.perform(put("/api/gouvernorats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGouvernorat)))
            .andExpect(status().isOk());

        // Validate the Gouvernorat in the database
        List<Gouvernorat> gouvernoratList = gouvernoratRepository.findAll();
        assertThat(gouvernoratList).hasSize(databaseSizeBeforeUpdate);
        Gouvernorat testGouvernorat = gouvernoratList.get(gouvernoratList.size() - 1);
        assertThat(testGouvernorat.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Gouvernorat in Elasticsearch
        Gouvernorat gouvernoratEs = gouvernoratSearchRepository.findOne(testGouvernorat.getId());
        assertThat(gouvernoratEs).isEqualToComparingFieldByField(testGouvernorat);
    }

    @Test
    @Transactional
    public void updateNonExistingGouvernorat() throws Exception {
        int databaseSizeBeforeUpdate = gouvernoratRepository.findAll().size();

        // Create the Gouvernorat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGouvernoratMockMvc.perform(put("/api/gouvernorats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gouvernorat)))
            .andExpect(status().isCreated());

        // Validate the Gouvernorat in the database
        List<Gouvernorat> gouvernoratList = gouvernoratRepository.findAll();
        assertThat(gouvernoratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGouvernorat() throws Exception {
        // Initialize the database
        gouvernoratRepository.saveAndFlush(gouvernorat);
        gouvernoratSearchRepository.save(gouvernorat);
        int databaseSizeBeforeDelete = gouvernoratRepository.findAll().size();

        // Get the gouvernorat
        restGouvernoratMockMvc.perform(delete("/api/gouvernorats/{id}", gouvernorat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gouvernoratExistsInEs = gouvernoratSearchRepository.exists(gouvernorat.getId());
        assertThat(gouvernoratExistsInEs).isFalse();

        // Validate the database is empty
        List<Gouvernorat> gouvernoratList = gouvernoratRepository.findAll();
        assertThat(gouvernoratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGouvernorat() throws Exception {
        // Initialize the database
        gouvernoratRepository.saveAndFlush(gouvernorat);
        gouvernoratSearchRepository.save(gouvernorat);

        // Search the gouvernorat
        restGouvernoratMockMvc.perform(get("/api/_search/gouvernorats?query=id:" + gouvernorat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gouvernorat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gouvernorat.class);
    }
}

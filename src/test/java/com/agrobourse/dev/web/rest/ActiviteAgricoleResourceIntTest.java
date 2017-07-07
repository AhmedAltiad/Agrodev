package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.ActiviteAgricole;
import com.agrobourse.dev.repository.ActiviteAgricoleRepository;
import com.agrobourse.dev.repository.search.ActiviteAgricoleSearchRepository;
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
 * Test class for the ActiviteAgricoleResource REST controller.
 *
 * @see ActiviteAgricoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class ActiviteAgricoleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ActiviteAgricoleRepository activiteAgricoleRepository;

    @Autowired
    private ActiviteAgricoleSearchRepository activiteAgricoleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActiviteAgricoleMockMvc;

    private ActiviteAgricole activiteAgricole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActiviteAgricoleResource activiteAgricoleResource = new ActiviteAgricoleResource(activiteAgricoleRepository, activiteAgricoleSearchRepository);
        this.restActiviteAgricoleMockMvc = MockMvcBuilders.standaloneSetup(activiteAgricoleResource)
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
    public static ActiviteAgricole createEntity(EntityManager em) {
        ActiviteAgricole activiteAgricole = new ActiviteAgricole()
            .name(DEFAULT_NAME);
        return activiteAgricole;
    }

    @Before
    public void initTest() {
        activiteAgricoleSearchRepository.deleteAll();
        activiteAgricole = createEntity(em);
    }

    @Test
    @Transactional
    public void createActiviteAgricole() throws Exception {
        int databaseSizeBeforeCreate = activiteAgricoleRepository.findAll().size();

        // Create the ActiviteAgricole
        restActiviteAgricoleMockMvc.perform(post("/api/activite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activiteAgricole)))
            .andExpect(status().isCreated());

        // Validate the ActiviteAgricole in the database
        List<ActiviteAgricole> activiteAgricoleList = activiteAgricoleRepository.findAll();
        assertThat(activiteAgricoleList).hasSize(databaseSizeBeforeCreate + 1);
        ActiviteAgricole testActiviteAgricole = activiteAgricoleList.get(activiteAgricoleList.size() - 1);
        assertThat(testActiviteAgricole.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the ActiviteAgricole in Elasticsearch
        ActiviteAgricole activiteAgricoleEs = activiteAgricoleSearchRepository.findOne(testActiviteAgricole.getId());
        assertThat(activiteAgricoleEs).isEqualToComparingFieldByField(testActiviteAgricole);
    }

    @Test
    @Transactional
    public void createActiviteAgricoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activiteAgricoleRepository.findAll().size();

        // Create the ActiviteAgricole with an existing ID
        activiteAgricole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteAgricoleMockMvc.perform(post("/api/activite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activiteAgricole)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ActiviteAgricole> activiteAgricoleList = activiteAgricoleRepository.findAll();
        assertThat(activiteAgricoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActiviteAgricoles() throws Exception {
        // Initialize the database
        activiteAgricoleRepository.saveAndFlush(activiteAgricole);

        // Get all the activiteAgricoleList
        restActiviteAgricoleMockMvc.perform(get("/api/activite-agricoles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activiteAgricole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getActiviteAgricole() throws Exception {
        // Initialize the database
        activiteAgricoleRepository.saveAndFlush(activiteAgricole);

        // Get the activiteAgricole
        restActiviteAgricoleMockMvc.perform(get("/api/activite-agricoles/{id}", activiteAgricole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activiteAgricole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActiviteAgricole() throws Exception {
        // Get the activiteAgricole
        restActiviteAgricoleMockMvc.perform(get("/api/activite-agricoles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActiviteAgricole() throws Exception {
        // Initialize the database
        activiteAgricoleRepository.saveAndFlush(activiteAgricole);
        activiteAgricoleSearchRepository.save(activiteAgricole);
        int databaseSizeBeforeUpdate = activiteAgricoleRepository.findAll().size();

        // Update the activiteAgricole
        ActiviteAgricole updatedActiviteAgricole = activiteAgricoleRepository.findOne(activiteAgricole.getId());
        updatedActiviteAgricole
            .name(UPDATED_NAME);

        restActiviteAgricoleMockMvc.perform(put("/api/activite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActiviteAgricole)))
            .andExpect(status().isOk());

        // Validate the ActiviteAgricole in the database
        List<ActiviteAgricole> activiteAgricoleList = activiteAgricoleRepository.findAll();
        assertThat(activiteAgricoleList).hasSize(databaseSizeBeforeUpdate);
        ActiviteAgricole testActiviteAgricole = activiteAgricoleList.get(activiteAgricoleList.size() - 1);
        assertThat(testActiviteAgricole.getName()).isEqualTo(UPDATED_NAME);

        // Validate the ActiviteAgricole in Elasticsearch
        ActiviteAgricole activiteAgricoleEs = activiteAgricoleSearchRepository.findOne(testActiviteAgricole.getId());
        assertThat(activiteAgricoleEs).isEqualToComparingFieldByField(testActiviteAgricole);
    }

    @Test
    @Transactional
    public void updateNonExistingActiviteAgricole() throws Exception {
        int databaseSizeBeforeUpdate = activiteAgricoleRepository.findAll().size();

        // Create the ActiviteAgricole

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActiviteAgricoleMockMvc.perform(put("/api/activite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activiteAgricole)))
            .andExpect(status().isCreated());

        // Validate the ActiviteAgricole in the database
        List<ActiviteAgricole> activiteAgricoleList = activiteAgricoleRepository.findAll();
        assertThat(activiteAgricoleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActiviteAgricole() throws Exception {
        // Initialize the database
        activiteAgricoleRepository.saveAndFlush(activiteAgricole);
        activiteAgricoleSearchRepository.save(activiteAgricole);
        int databaseSizeBeforeDelete = activiteAgricoleRepository.findAll().size();

        // Get the activiteAgricole
        restActiviteAgricoleMockMvc.perform(delete("/api/activite-agricoles/{id}", activiteAgricole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean activiteAgricoleExistsInEs = activiteAgricoleSearchRepository.exists(activiteAgricole.getId());
        assertThat(activiteAgricoleExistsInEs).isFalse();

        // Validate the database is empty
        List<ActiviteAgricole> activiteAgricoleList = activiteAgricoleRepository.findAll();
        assertThat(activiteAgricoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchActiviteAgricole() throws Exception {
        // Initialize the database
        activiteAgricoleRepository.saveAndFlush(activiteAgricole);
        activiteAgricoleSearchRepository.save(activiteAgricole);

        // Search the activiteAgricole
        restActiviteAgricoleMockMvc.perform(get("/api/_search/activite-agricoles?query=id:" + activiteAgricole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activiteAgricole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiviteAgricole.class);
    }
}

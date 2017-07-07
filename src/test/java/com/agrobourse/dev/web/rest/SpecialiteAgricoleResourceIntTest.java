package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.SpecialiteAgricole;
import com.agrobourse.dev.repository.SpecialiteAgricoleRepository;
import com.agrobourse.dev.repository.search.SpecialiteAgricoleSearchRepository;
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
 * Test class for the SpecialiteAgricoleResource REST controller.
 *
 * @see SpecialiteAgricoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class SpecialiteAgricoleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SpecialiteAgricoleRepository specialiteAgricoleRepository;

    @Autowired
    private SpecialiteAgricoleSearchRepository specialiteAgricoleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpecialiteAgricoleMockMvc;

    private SpecialiteAgricole specialiteAgricole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpecialiteAgricoleResource specialiteAgricoleResource = new SpecialiteAgricoleResource(specialiteAgricoleRepository, specialiteAgricoleSearchRepository);
        this.restSpecialiteAgricoleMockMvc = MockMvcBuilders.standaloneSetup(specialiteAgricoleResource)
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
    public static SpecialiteAgricole createEntity(EntityManager em) {
        SpecialiteAgricole specialiteAgricole = new SpecialiteAgricole()
            .name(DEFAULT_NAME);
        return specialiteAgricole;
    }

    @Before
    public void initTest() {
        specialiteAgricoleSearchRepository.deleteAll();
        specialiteAgricole = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialiteAgricole() throws Exception {
        int databaseSizeBeforeCreate = specialiteAgricoleRepository.findAll().size();

        // Create the SpecialiteAgricole
        restSpecialiteAgricoleMockMvc.perform(post("/api/specialite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteAgricole)))
            .andExpect(status().isCreated());

        // Validate the SpecialiteAgricole in the database
        List<SpecialiteAgricole> specialiteAgricoleList = specialiteAgricoleRepository.findAll();
        assertThat(specialiteAgricoleList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialiteAgricole testSpecialiteAgricole = specialiteAgricoleList.get(specialiteAgricoleList.size() - 1);
        assertThat(testSpecialiteAgricole.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the SpecialiteAgricole in Elasticsearch
        SpecialiteAgricole specialiteAgricoleEs = specialiteAgricoleSearchRepository.findOne(testSpecialiteAgricole.getId());
        assertThat(specialiteAgricoleEs).isEqualToComparingFieldByField(testSpecialiteAgricole);
    }

    @Test
    @Transactional
    public void createSpecialiteAgricoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialiteAgricoleRepository.findAll().size();

        // Create the SpecialiteAgricole with an existing ID
        specialiteAgricole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteAgricoleMockMvc.perform(post("/api/specialite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteAgricole)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SpecialiteAgricole> specialiteAgricoleList = specialiteAgricoleRepository.findAll();
        assertThat(specialiteAgricoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSpecialiteAgricoles() throws Exception {
        // Initialize the database
        specialiteAgricoleRepository.saveAndFlush(specialiteAgricole);

        // Get all the specialiteAgricoleList
        restSpecialiteAgricoleMockMvc.perform(get("/api/specialite-agricoles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialiteAgricole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSpecialiteAgricole() throws Exception {
        // Initialize the database
        specialiteAgricoleRepository.saveAndFlush(specialiteAgricole);

        // Get the specialiteAgricole
        restSpecialiteAgricoleMockMvc.perform(get("/api/specialite-agricoles/{id}", specialiteAgricole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialiteAgricole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialiteAgricole() throws Exception {
        // Get the specialiteAgricole
        restSpecialiteAgricoleMockMvc.perform(get("/api/specialite-agricoles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialiteAgricole() throws Exception {
        // Initialize the database
        specialiteAgricoleRepository.saveAndFlush(specialiteAgricole);
        specialiteAgricoleSearchRepository.save(specialiteAgricole);
        int databaseSizeBeforeUpdate = specialiteAgricoleRepository.findAll().size();

        // Update the specialiteAgricole
        SpecialiteAgricole updatedSpecialiteAgricole = specialiteAgricoleRepository.findOne(specialiteAgricole.getId());
        updatedSpecialiteAgricole
            .name(UPDATED_NAME);

        restSpecialiteAgricoleMockMvc.perform(put("/api/specialite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialiteAgricole)))
            .andExpect(status().isOk());

        // Validate the SpecialiteAgricole in the database
        List<SpecialiteAgricole> specialiteAgricoleList = specialiteAgricoleRepository.findAll();
        assertThat(specialiteAgricoleList).hasSize(databaseSizeBeforeUpdate);
        SpecialiteAgricole testSpecialiteAgricole = specialiteAgricoleList.get(specialiteAgricoleList.size() - 1);
        assertThat(testSpecialiteAgricole.getName()).isEqualTo(UPDATED_NAME);

        // Validate the SpecialiteAgricole in Elasticsearch
        SpecialiteAgricole specialiteAgricoleEs = specialiteAgricoleSearchRepository.findOne(testSpecialiteAgricole.getId());
        assertThat(specialiteAgricoleEs).isEqualToComparingFieldByField(testSpecialiteAgricole);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialiteAgricole() throws Exception {
        int databaseSizeBeforeUpdate = specialiteAgricoleRepository.findAll().size();

        // Create the SpecialiteAgricole

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpecialiteAgricoleMockMvc.perform(put("/api/specialite-agricoles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteAgricole)))
            .andExpect(status().isCreated());

        // Validate the SpecialiteAgricole in the database
        List<SpecialiteAgricole> specialiteAgricoleList = specialiteAgricoleRepository.findAll();
        assertThat(specialiteAgricoleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpecialiteAgricole() throws Exception {
        // Initialize the database
        specialiteAgricoleRepository.saveAndFlush(specialiteAgricole);
        specialiteAgricoleSearchRepository.save(specialiteAgricole);
        int databaseSizeBeforeDelete = specialiteAgricoleRepository.findAll().size();

        // Get the specialiteAgricole
        restSpecialiteAgricoleMockMvc.perform(delete("/api/specialite-agricoles/{id}", specialiteAgricole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean specialiteAgricoleExistsInEs = specialiteAgricoleSearchRepository.exists(specialiteAgricole.getId());
        assertThat(specialiteAgricoleExistsInEs).isFalse();

        // Validate the database is empty
        List<SpecialiteAgricole> specialiteAgricoleList = specialiteAgricoleRepository.findAll();
        assertThat(specialiteAgricoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSpecialiteAgricole() throws Exception {
        // Initialize the database
        specialiteAgricoleRepository.saveAndFlush(specialiteAgricole);
        specialiteAgricoleSearchRepository.save(specialiteAgricole);

        // Search the specialiteAgricole
        restSpecialiteAgricoleMockMvc.perform(get("/api/_search/specialite-agricoles?query=id:" + specialiteAgricole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialiteAgricole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialiteAgricole.class);
    }
}

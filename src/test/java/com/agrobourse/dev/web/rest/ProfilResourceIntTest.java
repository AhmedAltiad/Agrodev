package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Profil;
import com.agrobourse.dev.repository.ProfilRepository;
import com.agrobourse.dev.repository.search.ProfilSearchRepository;
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
 * Test class for the ProfilResource REST controller.
 *
 * @see ProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class ProfilResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private ProfilSearchRepository profilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfilMockMvc;

    private Profil profil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfilResource profilResource = new ProfilResource(profilRepository, profilSearchRepository);
        this.restProfilMockMvc = MockMvcBuilders.standaloneSetup(profilResource)
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
    public static Profil createEntity(EntityManager em) {
        Profil profil = new Profil()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .login(DEFAULT_LOGIN)
            .image(DEFAULT_IMAGE);
        return profil;
    }

    @Before
    public void initTest() {
        profilSearchRepository.deleteAll();
        profil = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfil() throws Exception {
        int databaseSizeBeforeCreate = profilRepository.findAll().size();

        // Create the Profil
        restProfilMockMvc.perform(post("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isCreated());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeCreate + 1);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProfil.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProfil.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfil.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testProfil.getImage()).isEqualTo(DEFAULT_IMAGE);

        // Validate the Profil in Elasticsearch
        Profil profilEs = profilSearchRepository.findOne(testProfil.getId());
        assertThat(profilEs).isEqualToComparingFieldByField(testProfil);
    }

    @Test
    @Transactional
    public void createProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profilRepository.findAll().size();

        // Create the Profil with an existing ID
        profil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfilMockMvc.perform(post("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfils() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList
        restProfilMockMvc.perform(get("/api/profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profil.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get the profil
        restProfilMockMvc.perform(get("/api/profils/{id}", profil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profil.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfil() throws Exception {
        // Get the profil
        restProfilMockMvc.perform(get("/api/profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);
        profilSearchRepository.save(profil);
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Update the profil
        Profil updatedProfil = profilRepository.findOne(profil.getId());
        updatedProfil
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .login(UPDATED_LOGIN)
            .image(UPDATED_IMAGE);

        restProfilMockMvc.perform(put("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfil)))
            .andExpect(status().isOk());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfil.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfil.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfil.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testProfil.getImage()).isEqualTo(UPDATED_IMAGE);

        // Validate the Profil in Elasticsearch
        Profil profilEs = profilSearchRepository.findOne(testProfil.getId());
        assertThat(profilEs).isEqualToComparingFieldByField(testProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Create the Profil

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfilMockMvc.perform(put("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isCreated());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);
        profilSearchRepository.save(profil);
        int databaseSizeBeforeDelete = profilRepository.findAll().size();

        // Get the profil
        restProfilMockMvc.perform(delete("/api/profils/{id}", profil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean profilExistsInEs = profilSearchRepository.exists(profil.getId());
        assertThat(profilExistsInEs).isFalse();

        // Validate the database is empty
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);
        profilSearchRepository.save(profil);

        // Search the profil
        restProfilMockMvc.perform(get("/api/_search/profils?query=id:" + profil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profil.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profil.class);
    }
}

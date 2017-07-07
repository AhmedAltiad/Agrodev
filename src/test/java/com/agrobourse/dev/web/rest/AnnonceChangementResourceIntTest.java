package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.AnnonceChangement;
import com.agrobourse.dev.repository.AnnonceChangementRepository;
import com.agrobourse.dev.repository.search.AnnonceChangementSearchRepository;
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
 * Test class for the AnnonceChangementResource REST controller.
 *
 * @see AnnonceChangementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class AnnonceChangementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHANGEMENT = 1;
    private static final Integer UPDATED_CHANGEMENT = 2;

    private static final ZonedDateTime DEFAULT_CREATEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AnnonceChangementRepository annonceChangementRepository;

    @Autowired
    private AnnonceChangementSearchRepository annonceChangementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnnonceChangementMockMvc;

    private AnnonceChangement annonceChangement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnnonceChangementResource annonceChangementResource = new AnnonceChangementResource(annonceChangementRepository, annonceChangementSearchRepository);
        this.restAnnonceChangementMockMvc = MockMvcBuilders.standaloneSetup(annonceChangementResource)
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
    public static AnnonceChangement createEntity(EntityManager em) {
        AnnonceChangement annonceChangement = new AnnonceChangement()
            .name(DEFAULT_NAME)
            .changement(DEFAULT_CHANGEMENT)
            .createddate(DEFAULT_CREATEDDATE);
        return annonceChangement;
    }

    @Before
    public void initTest() {
        annonceChangementSearchRepository.deleteAll();
        annonceChangement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnonceChangement() throws Exception {
        int databaseSizeBeforeCreate = annonceChangementRepository.findAll().size();

        // Create the AnnonceChangement
        restAnnonceChangementMockMvc.perform(post("/api/annonce-changements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceChangement)))
            .andExpect(status().isCreated());

        // Validate the AnnonceChangement in the database
        List<AnnonceChangement> annonceChangementList = annonceChangementRepository.findAll();
        assertThat(annonceChangementList).hasSize(databaseSizeBeforeCreate + 1);
        AnnonceChangement testAnnonceChangement = annonceChangementList.get(annonceChangementList.size() - 1);
        assertThat(testAnnonceChangement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnnonceChangement.getChangement()).isEqualTo(DEFAULT_CHANGEMENT);
        assertThat(testAnnonceChangement.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);

        // Validate the AnnonceChangement in Elasticsearch
        AnnonceChangement annonceChangementEs = annonceChangementSearchRepository.findOne(testAnnonceChangement.getId());
        assertThat(annonceChangementEs).isEqualToComparingFieldByField(testAnnonceChangement);
    }

    @Test
    @Transactional
    public void createAnnonceChangementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annonceChangementRepository.findAll().size();

        // Create the AnnonceChangement with an existing ID
        annonceChangement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnonceChangementMockMvc.perform(post("/api/annonce-changements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceChangement)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AnnonceChangement> annonceChangementList = annonceChangementRepository.findAll();
        assertThat(annonceChangementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnnonceChangements() throws Exception {
        // Initialize the database
        annonceChangementRepository.saveAndFlush(annonceChangement);

        // Get all the annonceChangementList
        restAnnonceChangementMockMvc.perform(get("/api/annonce-changements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonceChangement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].changement").value(hasItem(DEFAULT_CHANGEMENT)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))));
    }

    @Test
    @Transactional
    public void getAnnonceChangement() throws Exception {
        // Initialize the database
        annonceChangementRepository.saveAndFlush(annonceChangement);

        // Get the annonceChangement
        restAnnonceChangementMockMvc.perform(get("/api/annonce-changements/{id}", annonceChangement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annonceChangement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.changement").value(DEFAULT_CHANGEMENT))
            .andExpect(jsonPath("$.createddate").value(sameInstant(DEFAULT_CREATEDDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAnnonceChangement() throws Exception {
        // Get the annonceChangement
        restAnnonceChangementMockMvc.perform(get("/api/annonce-changements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnonceChangement() throws Exception {
        // Initialize the database
        annonceChangementRepository.saveAndFlush(annonceChangement);
        annonceChangementSearchRepository.save(annonceChangement);
        int databaseSizeBeforeUpdate = annonceChangementRepository.findAll().size();

        // Update the annonceChangement
        AnnonceChangement updatedAnnonceChangement = annonceChangementRepository.findOne(annonceChangement.getId());
        updatedAnnonceChangement
            .name(UPDATED_NAME)
            .changement(UPDATED_CHANGEMENT)
            .createddate(UPDATED_CREATEDDATE);

        restAnnonceChangementMockMvc.perform(put("/api/annonce-changements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnonceChangement)))
            .andExpect(status().isOk());

        // Validate the AnnonceChangement in the database
        List<AnnonceChangement> annonceChangementList = annonceChangementRepository.findAll();
        assertThat(annonceChangementList).hasSize(databaseSizeBeforeUpdate);
        AnnonceChangement testAnnonceChangement = annonceChangementList.get(annonceChangementList.size() - 1);
        assertThat(testAnnonceChangement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnnonceChangement.getChangement()).isEqualTo(UPDATED_CHANGEMENT);
        assertThat(testAnnonceChangement.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);

        // Validate the AnnonceChangement in Elasticsearch
        AnnonceChangement annonceChangementEs = annonceChangementSearchRepository.findOne(testAnnonceChangement.getId());
        assertThat(annonceChangementEs).isEqualToComparingFieldByField(testAnnonceChangement);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnonceChangement() throws Exception {
        int databaseSizeBeforeUpdate = annonceChangementRepository.findAll().size();

        // Create the AnnonceChangement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnnonceChangementMockMvc.perform(put("/api/annonce-changements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceChangement)))
            .andExpect(status().isCreated());

        // Validate the AnnonceChangement in the database
        List<AnnonceChangement> annonceChangementList = annonceChangementRepository.findAll();
        assertThat(annonceChangementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnnonceChangement() throws Exception {
        // Initialize the database
        annonceChangementRepository.saveAndFlush(annonceChangement);
        annonceChangementSearchRepository.save(annonceChangement);
        int databaseSizeBeforeDelete = annonceChangementRepository.findAll().size();

        // Get the annonceChangement
        restAnnonceChangementMockMvc.perform(delete("/api/annonce-changements/{id}", annonceChangement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean annonceChangementExistsInEs = annonceChangementSearchRepository.exists(annonceChangement.getId());
        assertThat(annonceChangementExistsInEs).isFalse();

        // Validate the database is empty
        List<AnnonceChangement> annonceChangementList = annonceChangementRepository.findAll();
        assertThat(annonceChangementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAnnonceChangement() throws Exception {
        // Initialize the database
        annonceChangementRepository.saveAndFlush(annonceChangement);
        annonceChangementSearchRepository.save(annonceChangement);

        // Search the annonceChangement
        restAnnonceChangementMockMvc.perform(get("/api/_search/annonce-changements?query=id:" + annonceChangement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonceChangement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].changement").value(hasItem(DEFAULT_CHANGEMENT)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnonceChangement.class);
    }
}

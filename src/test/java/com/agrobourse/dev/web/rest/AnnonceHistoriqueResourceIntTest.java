package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.AnnonceHistorique;
import com.agrobourse.dev.repository.AnnonceHistoriqueRepository;
import com.agrobourse.dev.repository.search.AnnonceHistoriqueSearchRepository;
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
 * Test class for the AnnonceHistoriqueResource REST controller.
 *
 * @see AnnonceHistoriqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class AnnonceHistoriqueResourceIntTest {

    private static final Long DEFAULT_NUM_ANNONCE = 1L;
    private static final Long UPDATED_NUM_ANNONCE = 2L;

    private static final Integer DEFAULT_ETAT = 1;
    private static final Integer UPDATED_ETAT = 2;

    private static final ZonedDateTime DEFAULT_CREATEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LASTMODIFIEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LASTMODIFIEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATEACTIVATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEACTIVATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEEXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEEXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AnnonceHistoriqueRepository annonceHistoriqueRepository;

    @Autowired
    private AnnonceHistoriqueSearchRepository annonceHistoriqueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnnonceHistoriqueMockMvc;

    private AnnonceHistorique annonceHistorique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnnonceHistoriqueResource annonceHistoriqueResource = new AnnonceHistoriqueResource(annonceHistoriqueRepository, annonceHistoriqueSearchRepository);
        this.restAnnonceHistoriqueMockMvc = MockMvcBuilders.standaloneSetup(annonceHistoriqueResource)
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
    public static AnnonceHistorique createEntity(EntityManager em) {
        AnnonceHistorique annonceHistorique = new AnnonceHistorique()
            .numAnnonce(DEFAULT_NUM_ANNONCE)
            .etat(DEFAULT_ETAT)
            .createddate(DEFAULT_CREATEDDATE)
            .lastmodifieddate(DEFAULT_LASTMODIFIEDDATE)
            .dateactivation(DEFAULT_DATEACTIVATION)
            .dateexpiration(DEFAULT_DATEEXPIRATION)
            .prix(DEFAULT_PRIX)
            .quantite(DEFAULT_QUANTITE)
            .image(DEFAULT_IMAGE)
            .description(DEFAULT_DESCRIPTION);
        return annonceHistorique;
    }

    @Before
    public void initTest() {
        annonceHistoriqueSearchRepository.deleteAll();
        annonceHistorique = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnonceHistorique() throws Exception {
        int databaseSizeBeforeCreate = annonceHistoriqueRepository.findAll().size();

        // Create the AnnonceHistorique
        restAnnonceHistoriqueMockMvc.perform(post("/api/annonce-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceHistorique)))
            .andExpect(status().isCreated());

        // Validate the AnnonceHistorique in the database
        List<AnnonceHistorique> annonceHistoriqueList = annonceHistoriqueRepository.findAll();
        assertThat(annonceHistoriqueList).hasSize(databaseSizeBeforeCreate + 1);
        AnnonceHistorique testAnnonceHistorique = annonceHistoriqueList.get(annonceHistoriqueList.size() - 1);
        assertThat(testAnnonceHistorique.getNumAnnonce()).isEqualTo(DEFAULT_NUM_ANNONCE);
        assertThat(testAnnonceHistorique.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testAnnonceHistorique.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testAnnonceHistorique.getLastmodifieddate()).isEqualTo(DEFAULT_LASTMODIFIEDDATE);
        assertThat(testAnnonceHistorique.getDateactivation()).isEqualTo(DEFAULT_DATEACTIVATION);
        assertThat(testAnnonceHistorique.getDateexpiration()).isEqualTo(DEFAULT_DATEEXPIRATION);
        assertThat(testAnnonceHistorique.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testAnnonceHistorique.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testAnnonceHistorique.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAnnonceHistorique.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the AnnonceHistorique in Elasticsearch
        AnnonceHistorique annonceHistoriqueEs = annonceHistoriqueSearchRepository.findOne(testAnnonceHistorique.getId());
        assertThat(annonceHistoriqueEs).isEqualToComparingFieldByField(testAnnonceHistorique);
    }

    @Test
    @Transactional
    public void createAnnonceHistoriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annonceHistoriqueRepository.findAll().size();

        // Create the AnnonceHistorique with an existing ID
        annonceHistorique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnonceHistoriqueMockMvc.perform(post("/api/annonce-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceHistorique)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AnnonceHistorique> annonceHistoriqueList = annonceHistoriqueRepository.findAll();
        assertThat(annonceHistoriqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnnonceHistoriques() throws Exception {
        // Initialize the database
        annonceHistoriqueRepository.saveAndFlush(annonceHistorique);

        // Get all the annonceHistoriqueList
        restAnnonceHistoriqueMockMvc.perform(get("/api/annonce-historiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonceHistorique.getId().intValue())))
            .andExpect(jsonPath("$.[*].numAnnonce").value(hasItem(DEFAULT_NUM_ANNONCE.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATE))))
            .andExpect(jsonPath("$.[*].dateactivation").value(hasItem(DEFAULT_DATEACTIVATION.toString())))
            .andExpect(jsonPath("$.[*].dateexpiration").value(hasItem(DEFAULT_DATEEXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAnnonceHistorique() throws Exception {
        // Initialize the database
        annonceHistoriqueRepository.saveAndFlush(annonceHistorique);

        // Get the annonceHistorique
        restAnnonceHistoriqueMockMvc.perform(get("/api/annonce-historiques/{id}", annonceHistorique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annonceHistorique.getId().intValue()))
            .andExpect(jsonPath("$.numAnnonce").value(DEFAULT_NUM_ANNONCE.intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.createddate").value(sameInstant(DEFAULT_CREATEDDATE)))
            .andExpect(jsonPath("$.lastmodifieddate").value(sameInstant(DEFAULT_LASTMODIFIEDDATE)))
            .andExpect(jsonPath("$.dateactivation").value(DEFAULT_DATEACTIVATION.toString()))
            .andExpect(jsonPath("$.dateexpiration").value(DEFAULT_DATEEXPIRATION.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnonceHistorique() throws Exception {
        // Get the annonceHistorique
        restAnnonceHistoriqueMockMvc.perform(get("/api/annonce-historiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnonceHistorique() throws Exception {
        // Initialize the database
        annonceHistoriqueRepository.saveAndFlush(annonceHistorique);
        annonceHistoriqueSearchRepository.save(annonceHistorique);
        int databaseSizeBeforeUpdate = annonceHistoriqueRepository.findAll().size();

        // Update the annonceHistorique
        AnnonceHistorique updatedAnnonceHistorique = annonceHistoriqueRepository.findOne(annonceHistorique.getId());
        updatedAnnonceHistorique
            .numAnnonce(UPDATED_NUM_ANNONCE)
            .etat(UPDATED_ETAT)
            .createddate(UPDATED_CREATEDDATE)
            .lastmodifieddate(UPDATED_LASTMODIFIEDDATE)
            .dateactivation(UPDATED_DATEACTIVATION)
            .dateexpiration(UPDATED_DATEEXPIRATION)
            .prix(UPDATED_PRIX)
            .quantite(UPDATED_QUANTITE)
            .image(UPDATED_IMAGE)
            .description(UPDATED_DESCRIPTION);

        restAnnonceHistoriqueMockMvc.perform(put("/api/annonce-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnonceHistorique)))
            .andExpect(status().isOk());

        // Validate the AnnonceHistorique in the database
        List<AnnonceHistorique> annonceHistoriqueList = annonceHistoriqueRepository.findAll();
        assertThat(annonceHistoriqueList).hasSize(databaseSizeBeforeUpdate);
        AnnonceHistorique testAnnonceHistorique = annonceHistoriqueList.get(annonceHistoriqueList.size() - 1);
        assertThat(testAnnonceHistorique.getNumAnnonce()).isEqualTo(UPDATED_NUM_ANNONCE);
        assertThat(testAnnonceHistorique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testAnnonceHistorique.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testAnnonceHistorique.getLastmodifieddate()).isEqualTo(UPDATED_LASTMODIFIEDDATE);
        assertThat(testAnnonceHistorique.getDateactivation()).isEqualTo(UPDATED_DATEACTIVATION);
        assertThat(testAnnonceHistorique.getDateexpiration()).isEqualTo(UPDATED_DATEEXPIRATION);
        assertThat(testAnnonceHistorique.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testAnnonceHistorique.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testAnnonceHistorique.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAnnonceHistorique.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the AnnonceHistorique in Elasticsearch
        AnnonceHistorique annonceHistoriqueEs = annonceHistoriqueSearchRepository.findOne(testAnnonceHistorique.getId());
        assertThat(annonceHistoriqueEs).isEqualToComparingFieldByField(testAnnonceHistorique);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnonceHistorique() throws Exception {
        int databaseSizeBeforeUpdate = annonceHistoriqueRepository.findAll().size();

        // Create the AnnonceHistorique

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnnonceHistoriqueMockMvc.perform(put("/api/annonce-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonceHistorique)))
            .andExpect(status().isCreated());

        // Validate the AnnonceHistorique in the database
        List<AnnonceHistorique> annonceHistoriqueList = annonceHistoriqueRepository.findAll();
        assertThat(annonceHistoriqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnnonceHistorique() throws Exception {
        // Initialize the database
        annonceHistoriqueRepository.saveAndFlush(annonceHistorique);
        annonceHistoriqueSearchRepository.save(annonceHistorique);
        int databaseSizeBeforeDelete = annonceHistoriqueRepository.findAll().size();

        // Get the annonceHistorique
        restAnnonceHistoriqueMockMvc.perform(delete("/api/annonce-historiques/{id}", annonceHistorique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean annonceHistoriqueExistsInEs = annonceHistoriqueSearchRepository.exists(annonceHistorique.getId());
        assertThat(annonceHistoriqueExistsInEs).isFalse();

        // Validate the database is empty
        List<AnnonceHistorique> annonceHistoriqueList = annonceHistoriqueRepository.findAll();
        assertThat(annonceHistoriqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAnnonceHistorique() throws Exception {
        // Initialize the database
        annonceHistoriqueRepository.saveAndFlush(annonceHistorique);
        annonceHistoriqueSearchRepository.save(annonceHistorique);

        // Search the annonceHistorique
        restAnnonceHistoriqueMockMvc.perform(get("/api/_search/annonce-historiques?query=id:" + annonceHistorique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonceHistorique.getId().intValue())))
            .andExpect(jsonPath("$.[*].numAnnonce").value(hasItem(DEFAULT_NUM_ANNONCE.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATE))))
            .andExpect(jsonPath("$.[*].dateactivation").value(hasItem(DEFAULT_DATEACTIVATION.toString())))
            .andExpect(jsonPath("$.[*].dateexpiration").value(hasItem(DEFAULT_DATEEXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnonceHistorique.class);
    }
}

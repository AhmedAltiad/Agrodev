package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Annonce;
import com.agrobourse.dev.repository.AnnonceRepository;
import com.agrobourse.dev.repository.search.AnnonceSearchRepository;
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
 * Test class for the AnnonceResource REST controller.
 *
 * @see AnnonceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class AnnonceResourceIntTest {

    private static final Long DEFAULT_NUMANN = 1L;
    private static final Long UPDATED_NUMANN = 2L;

    private static final Integer DEFAULT_ETAT = 1;
    private static final Integer UPDATED_ETAT = 2;

    private static final ZonedDateTime DEFAULT_CREATEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LASTMODIFIEDDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LASTMODIFIEDDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATE_ACTIVATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACTIVATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private AnnonceSearchRepository annonceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnnonceMockMvc;

    private Annonce annonce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnnonceResource annonceResource = new AnnonceResource(annonceRepository, annonceSearchRepository);
        this.restAnnonceMockMvc = MockMvcBuilders.standaloneSetup(annonceResource)
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
    public static Annonce createEntity(EntityManager em) {
        Annonce annonce = new Annonce()
            .numann(DEFAULT_NUMANN)
            .etat(DEFAULT_ETAT)
            .createddate(DEFAULT_CREATEDDATE)
            .lastmodifieddate(DEFAULT_LASTMODIFIEDDATE)
            .dateActivation(DEFAULT_DATE_ACTIVATION)
            .dateExpiration(DEFAULT_DATE_EXPIRATION)
            .prix(DEFAULT_PRIX)
            .quantite(DEFAULT_QUANTITE)
            .image(DEFAULT_IMAGE)
            .description(DEFAULT_DESCRIPTION);
        return annonce;
    }

    @Before
    public void initTest() {
        annonceSearchRepository.deleteAll();
        annonce = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnonce() throws Exception {
        int databaseSizeBeforeCreate = annonceRepository.findAll().size();

        // Create the Annonce
        restAnnonceMockMvc.perform(post("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonce)))
            .andExpect(status().isCreated());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeCreate + 1);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getNumann()).isEqualTo(DEFAULT_NUMANN);
        assertThat(testAnnonce.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testAnnonce.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testAnnonce.getLastmodifieddate()).isEqualTo(DEFAULT_LASTMODIFIEDDATE);
        assertThat(testAnnonce.getDateActivation()).isEqualTo(DEFAULT_DATE_ACTIVATION);
        assertThat(testAnnonce.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testAnnonce.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testAnnonce.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testAnnonce.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAnnonce.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Annonce in Elasticsearch
        Annonce annonceEs = annonceSearchRepository.findOne(testAnnonce.getId());
        assertThat(annonceEs).isEqualToComparingFieldByField(testAnnonce);
    }

    @Test
    @Transactional
    public void createAnnonceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annonceRepository.findAll().size();

        // Create the Annonce with an existing ID
        annonce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnonceMockMvc.perform(post("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonce)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnnonces() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        // Get all the annonceList
        restAnnonceMockMvc.perform(get("/api/annonces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].numann").value(hasItem(DEFAULT_NUMANN.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATE))))
            .andExpect(jsonPath("$.[*].dateActivation").value(hasItem(DEFAULT_DATE_ACTIVATION.toString())))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(DEFAULT_DATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        // Get the annonce
        restAnnonceMockMvc.perform(get("/api/annonces/{id}", annonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annonce.getId().intValue()))
            .andExpect(jsonPath("$.numann").value(DEFAULT_NUMANN.intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.createddate").value(sameInstant(DEFAULT_CREATEDDATE)))
            .andExpect(jsonPath("$.lastmodifieddate").value(sameInstant(DEFAULT_LASTMODIFIEDDATE)))
            .andExpect(jsonPath("$.dateActivation").value(DEFAULT_DATE_ACTIVATION.toString()))
            .andExpect(jsonPath("$.dateExpiration").value(DEFAULT_DATE_EXPIRATION.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnonce() throws Exception {
        // Get the annonce
        restAnnonceMockMvc.perform(get("/api/annonces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);
        annonceSearchRepository.save(annonce);
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Update the annonce
        Annonce updatedAnnonce = annonceRepository.findOne(annonce.getId());
        updatedAnnonce
            .numann(UPDATED_NUMANN)
            .etat(UPDATED_ETAT)
            .createddate(UPDATED_CREATEDDATE)
            .lastmodifieddate(UPDATED_LASTMODIFIEDDATE)
            .dateActivation(UPDATED_DATE_ACTIVATION)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .prix(UPDATED_PRIX)
            .quantite(UPDATED_QUANTITE)
            .image(UPDATED_IMAGE)
            .description(UPDATED_DESCRIPTION);

        restAnnonceMockMvc.perform(put("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnonce)))
            .andExpect(status().isOk());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getNumann()).isEqualTo(UPDATED_NUMANN);
        assertThat(testAnnonce.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testAnnonce.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testAnnonce.getLastmodifieddate()).isEqualTo(UPDATED_LASTMODIFIEDDATE);
        assertThat(testAnnonce.getDateActivation()).isEqualTo(UPDATED_DATE_ACTIVATION);
        assertThat(testAnnonce.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testAnnonce.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testAnnonce.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testAnnonce.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAnnonce.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Annonce in Elasticsearch
        Annonce annonceEs = annonceSearchRepository.findOne(testAnnonce.getId());
        assertThat(annonceEs).isEqualToComparingFieldByField(testAnnonce);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Create the Annonce

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnnonceMockMvc.perform(put("/api/annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annonce)))
            .andExpect(status().isCreated());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);
        annonceSearchRepository.save(annonce);
        int databaseSizeBeforeDelete = annonceRepository.findAll().size();

        // Get the annonce
        restAnnonceMockMvc.perform(delete("/api/annonces/{id}", annonce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean annonceExistsInEs = annonceSearchRepository.exists(annonce.getId());
        assertThat(annonceExistsInEs).isFalse();

        // Validate the database is empty
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);
        annonceSearchRepository.save(annonce);

        // Search the annonce
        restAnnonceMockMvc.perform(get("/api/_search/annonces?query=id:" + annonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].numann").value(hasItem(DEFAULT_NUMANN.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(sameInstant(DEFAULT_CREATEDDATE))))
            .andExpect(jsonPath("$.[*].lastmodifieddate").value(hasItem(sameInstant(DEFAULT_LASTMODIFIEDDATE))))
            .andExpect(jsonPath("$.[*].dateActivation").value(hasItem(DEFAULT_DATE_ACTIVATION.toString())))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(DEFAULT_DATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Annonce.class);
    }
}

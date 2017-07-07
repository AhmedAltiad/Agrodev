package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.ECommande;
import com.agrobourse.dev.repository.ECommandeRepository;
import com.agrobourse.dev.repository.search.ECommandeSearchRepository;
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
 * Test class for the ECommandeResource REST controller.
 *
 * @see ECommandeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class ECommandeResourceIntTest {

    private static final Integer DEFAULT_ETAT = 1;
    private static final Integer UPDATED_ETAT = 2;

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_NUMCOMMANDE = 1L;
    private static final Long UPDATED_NUMCOMMANDE = 2L;

    @Autowired
    private ECommandeRepository eCommandeRepository;

    @Autowired
    private ECommandeSearchRepository eCommandeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restECommandeMockMvc;

    private ECommande eCommande;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ECommandeResource eCommandeResource = new ECommandeResource(eCommandeRepository, eCommandeSearchRepository);
        this.restECommandeMockMvc = MockMvcBuilders.standaloneSetup(eCommandeResource)
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
    public static ECommande createEntity(EntityManager em) {
        ECommande eCommande = new ECommande()
            .etat(DEFAULT_ETAT)
            .prix(DEFAULT_PRIX)
            .quantite(DEFAULT_QUANTITE)
            .date(DEFAULT_DATE)
            .numcommande(DEFAULT_NUMCOMMANDE);
        return eCommande;
    }

    @Before
    public void initTest() {
        eCommandeSearchRepository.deleteAll();
        eCommande = createEntity(em);
    }

    @Test
    @Transactional
    public void createECommande() throws Exception {
        int databaseSizeBeforeCreate = eCommandeRepository.findAll().size();

        // Create the ECommande
        restECommandeMockMvc.perform(post("/api/e-commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eCommande)))
            .andExpect(status().isCreated());

        // Validate the ECommande in the database
        List<ECommande> eCommandeList = eCommandeRepository.findAll();
        assertThat(eCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        ECommande testECommande = eCommandeList.get(eCommandeList.size() - 1);
        assertThat(testECommande.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testECommande.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testECommande.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testECommande.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testECommande.getNumcommande()).isEqualTo(DEFAULT_NUMCOMMANDE);

        // Validate the ECommande in Elasticsearch
        ECommande eCommandeEs = eCommandeSearchRepository.findOne(testECommande.getId());
        assertThat(eCommandeEs).isEqualToComparingFieldByField(testECommande);
    }

    @Test
    @Transactional
    public void createECommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eCommandeRepository.findAll().size();

        // Create the ECommande with an existing ID
        eCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restECommandeMockMvc.perform(post("/api/e-commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eCommande)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ECommande> eCommandeList = eCommandeRepository.findAll();
        assertThat(eCommandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllECommandes() throws Exception {
        // Initialize the database
        eCommandeRepository.saveAndFlush(eCommande);

        // Get all the eCommandeList
        restECommandeMockMvc.perform(get("/api/e-commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numcommande").value(hasItem(DEFAULT_NUMCOMMANDE.intValue())));
    }

    @Test
    @Transactional
    public void getECommande() throws Exception {
        // Initialize the database
        eCommandeRepository.saveAndFlush(eCommande);

        // Get the eCommande
        restECommandeMockMvc.perform(get("/api/e-commandes/{id}", eCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eCommande.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.numcommande").value(DEFAULT_NUMCOMMANDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingECommande() throws Exception {
        // Get the eCommande
        restECommandeMockMvc.perform(get("/api/e-commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateECommande() throws Exception {
        // Initialize the database
        eCommandeRepository.saveAndFlush(eCommande);
        eCommandeSearchRepository.save(eCommande);
        int databaseSizeBeforeUpdate = eCommandeRepository.findAll().size();

        // Update the eCommande
        ECommande updatedECommande = eCommandeRepository.findOne(eCommande.getId());
        updatedECommande
            .etat(UPDATED_ETAT)
            .prix(UPDATED_PRIX)
            .quantite(UPDATED_QUANTITE)
            .date(UPDATED_DATE)
            .numcommande(UPDATED_NUMCOMMANDE);

        restECommandeMockMvc.perform(put("/api/e-commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedECommande)))
            .andExpect(status().isOk());

        // Validate the ECommande in the database
        List<ECommande> eCommandeList = eCommandeRepository.findAll();
        assertThat(eCommandeList).hasSize(databaseSizeBeforeUpdate);
        ECommande testECommande = eCommandeList.get(eCommandeList.size() - 1);
        assertThat(testECommande.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testECommande.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testECommande.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testECommande.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testECommande.getNumcommande()).isEqualTo(UPDATED_NUMCOMMANDE);

        // Validate the ECommande in Elasticsearch
        ECommande eCommandeEs = eCommandeSearchRepository.findOne(testECommande.getId());
        assertThat(eCommandeEs).isEqualToComparingFieldByField(testECommande);
    }

    @Test
    @Transactional
    public void updateNonExistingECommande() throws Exception {
        int databaseSizeBeforeUpdate = eCommandeRepository.findAll().size();

        // Create the ECommande

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restECommandeMockMvc.perform(put("/api/e-commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eCommande)))
            .andExpect(status().isCreated());

        // Validate the ECommande in the database
        List<ECommande> eCommandeList = eCommandeRepository.findAll();
        assertThat(eCommandeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteECommande() throws Exception {
        // Initialize the database
        eCommandeRepository.saveAndFlush(eCommande);
        eCommandeSearchRepository.save(eCommande);
        int databaseSizeBeforeDelete = eCommandeRepository.findAll().size();

        // Get the eCommande
        restECommandeMockMvc.perform(delete("/api/e-commandes/{id}", eCommande.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eCommandeExistsInEs = eCommandeSearchRepository.exists(eCommande.getId());
        assertThat(eCommandeExistsInEs).isFalse();

        // Validate the database is empty
        List<ECommande> eCommandeList = eCommandeRepository.findAll();
        assertThat(eCommandeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchECommande() throws Exception {
        // Initialize the database
        eCommandeRepository.saveAndFlush(eCommande);
        eCommandeSearchRepository.save(eCommande);

        // Search the eCommande
        restECommandeMockMvc.perform(get("/api/_search/e-commandes?query=id:" + eCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numcommande").value(hasItem(DEFAULT_NUMCOMMANDE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ECommande.class);
    }
}

package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.ECommandeHistorique;
import com.agrobourse.dev.repository.ECommandeHistoriqueRepository;
import com.agrobourse.dev.repository.search.ECommandeHistoriqueSearchRepository;
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
 * Test class for the ECommandeHistoriqueResource REST controller.
 *
 * @see ECommandeHistoriqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class ECommandeHistoriqueResourceIntTest {

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
    private ECommandeHistoriqueRepository eCommandeHistoriqueRepository;

    @Autowired
    private ECommandeHistoriqueSearchRepository eCommandeHistoriqueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restECommandeHistoriqueMockMvc;

    private ECommandeHistorique eCommandeHistorique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ECommandeHistoriqueResource eCommandeHistoriqueResource = new ECommandeHistoriqueResource(eCommandeHistoriqueRepository, eCommandeHistoriqueSearchRepository);
        this.restECommandeHistoriqueMockMvc = MockMvcBuilders.standaloneSetup(eCommandeHistoriqueResource)
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
    public static ECommandeHistorique createEntity(EntityManager em) {
        ECommandeHistorique eCommandeHistorique = new ECommandeHistorique()
            .etat(DEFAULT_ETAT)
            .prix(DEFAULT_PRIX)
            .quantite(DEFAULT_QUANTITE)
            .date(DEFAULT_DATE)
            .numcommande(DEFAULT_NUMCOMMANDE);
        return eCommandeHistorique;
    }

    @Before
    public void initTest() {
        eCommandeHistoriqueSearchRepository.deleteAll();
        eCommandeHistorique = createEntity(em);
    }

    @Test
    @Transactional
    public void createECommandeHistorique() throws Exception {
        int databaseSizeBeforeCreate = eCommandeHistoriqueRepository.findAll().size();

        // Create the ECommandeHistorique
        restECommandeHistoriqueMockMvc.perform(post("/api/e-commande-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eCommandeHistorique)))
            .andExpect(status().isCreated());

        // Validate the ECommandeHistorique in the database
        List<ECommandeHistorique> eCommandeHistoriqueList = eCommandeHistoriqueRepository.findAll();
        assertThat(eCommandeHistoriqueList).hasSize(databaseSizeBeforeCreate + 1);
        ECommandeHistorique testECommandeHistorique = eCommandeHistoriqueList.get(eCommandeHistoriqueList.size() - 1);
        assertThat(testECommandeHistorique.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testECommandeHistorique.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testECommandeHistorique.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testECommandeHistorique.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testECommandeHistorique.getNumcommande()).isEqualTo(DEFAULT_NUMCOMMANDE);

        // Validate the ECommandeHistorique in Elasticsearch
        ECommandeHistorique eCommandeHistoriqueEs = eCommandeHistoriqueSearchRepository.findOne(testECommandeHistorique.getId());
        assertThat(eCommandeHistoriqueEs).isEqualToComparingFieldByField(testECommandeHistorique);
    }

    @Test
    @Transactional
    public void createECommandeHistoriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eCommandeHistoriqueRepository.findAll().size();

        // Create the ECommandeHistorique with an existing ID
        eCommandeHistorique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restECommandeHistoriqueMockMvc.perform(post("/api/e-commande-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eCommandeHistorique)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ECommandeHistorique> eCommandeHistoriqueList = eCommandeHistoriqueRepository.findAll();
        assertThat(eCommandeHistoriqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllECommandeHistoriques() throws Exception {
        // Initialize the database
        eCommandeHistoriqueRepository.saveAndFlush(eCommandeHistorique);

        // Get all the eCommandeHistoriqueList
        restECommandeHistoriqueMockMvc.perform(get("/api/e-commande-historiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eCommandeHistorique.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numcommande").value(hasItem(DEFAULT_NUMCOMMANDE.intValue())));
    }

    @Test
    @Transactional
    public void getECommandeHistorique() throws Exception {
        // Initialize the database
        eCommandeHistoriqueRepository.saveAndFlush(eCommandeHistorique);

        // Get the eCommandeHistorique
        restECommandeHistoriqueMockMvc.perform(get("/api/e-commande-historiques/{id}", eCommandeHistorique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eCommandeHistorique.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.numcommande").value(DEFAULT_NUMCOMMANDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingECommandeHistorique() throws Exception {
        // Get the eCommandeHistorique
        restECommandeHistoriqueMockMvc.perform(get("/api/e-commande-historiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateECommandeHistorique() throws Exception {
        // Initialize the database
        eCommandeHistoriqueRepository.saveAndFlush(eCommandeHistorique);
        eCommandeHistoriqueSearchRepository.save(eCommandeHistorique);
        int databaseSizeBeforeUpdate = eCommandeHistoriqueRepository.findAll().size();

        // Update the eCommandeHistorique
        ECommandeHistorique updatedECommandeHistorique = eCommandeHistoriqueRepository.findOne(eCommandeHistorique.getId());
        updatedECommandeHistorique
            .etat(UPDATED_ETAT)
            .prix(UPDATED_PRIX)
            .quantite(UPDATED_QUANTITE)
            .date(UPDATED_DATE)
            .numcommande(UPDATED_NUMCOMMANDE);

        restECommandeHistoriqueMockMvc.perform(put("/api/e-commande-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedECommandeHistorique)))
            .andExpect(status().isOk());

        // Validate the ECommandeHistorique in the database
        List<ECommandeHistorique> eCommandeHistoriqueList = eCommandeHistoriqueRepository.findAll();
        assertThat(eCommandeHistoriqueList).hasSize(databaseSizeBeforeUpdate);
        ECommandeHistorique testECommandeHistorique = eCommandeHistoriqueList.get(eCommandeHistoriqueList.size() - 1);
        assertThat(testECommandeHistorique.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testECommandeHistorique.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testECommandeHistorique.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testECommandeHistorique.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testECommandeHistorique.getNumcommande()).isEqualTo(UPDATED_NUMCOMMANDE);

        // Validate the ECommandeHistorique in Elasticsearch
        ECommandeHistorique eCommandeHistoriqueEs = eCommandeHistoriqueSearchRepository.findOne(testECommandeHistorique.getId());
        assertThat(eCommandeHistoriqueEs).isEqualToComparingFieldByField(testECommandeHistorique);
    }

    @Test
    @Transactional
    public void updateNonExistingECommandeHistorique() throws Exception {
        int databaseSizeBeforeUpdate = eCommandeHistoriqueRepository.findAll().size();

        // Create the ECommandeHistorique

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restECommandeHistoriqueMockMvc.perform(put("/api/e-commande-historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eCommandeHistorique)))
            .andExpect(status().isCreated());

        // Validate the ECommandeHistorique in the database
        List<ECommandeHistorique> eCommandeHistoriqueList = eCommandeHistoriqueRepository.findAll();
        assertThat(eCommandeHistoriqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteECommandeHistorique() throws Exception {
        // Initialize the database
        eCommandeHistoriqueRepository.saveAndFlush(eCommandeHistorique);
        eCommandeHistoriqueSearchRepository.save(eCommandeHistorique);
        int databaseSizeBeforeDelete = eCommandeHistoriqueRepository.findAll().size();

        // Get the eCommandeHistorique
        restECommandeHistoriqueMockMvc.perform(delete("/api/e-commande-historiques/{id}", eCommandeHistorique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eCommandeHistoriqueExistsInEs = eCommandeHistoriqueSearchRepository.exists(eCommandeHistorique.getId());
        assertThat(eCommandeHistoriqueExistsInEs).isFalse();

        // Validate the database is empty
        List<ECommandeHistorique> eCommandeHistoriqueList = eCommandeHistoriqueRepository.findAll();
        assertThat(eCommandeHistoriqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchECommandeHistorique() throws Exception {
        // Initialize the database
        eCommandeHistoriqueRepository.saveAndFlush(eCommandeHistorique);
        eCommandeHistoriqueSearchRepository.save(eCommandeHistorique);

        // Search the eCommandeHistorique
        restECommandeHistoriqueMockMvc.perform(get("/api/_search/e-commande-historiques?query=id:" + eCommandeHistorique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eCommandeHistorique.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numcommande").value(hasItem(DEFAULT_NUMCOMMANDE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ECommandeHistorique.class);
    }
}

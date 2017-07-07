package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Agriculteur;
import com.agrobourse.dev.repository.AgriculteurRepository;
import com.agrobourse.dev.repository.search.AgriculteurSearchRepository;
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
 * Test class for the AgriculteurResource REST controller.
 *
 * @see AgriculteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class AgriculteurResourceIntTest {

    private static final String DEFAULT_TYPE_SOCIETE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SOCIETE = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIVRAISON = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_AGRICOLE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_AGRICOLE = "BBBBBBBBBB";

    private static final Double DEFAULT_SUPERFICIE = 1D;
    private static final Double UPDATED_SUPERFICIE = 2D;

    private static final Double DEFAULT_SUPERFICIE_IRRIGUEE = 1D;
    private static final Double UPDATED_SUPERFICIE_IRRIGUEE = 2D;

    private static final Integer DEFAULT_BIO = 1;
    private static final Integer UPDATED_BIO = 2;

    private static final Integer DEFAULT_NBRE_EMPLOYEE_PERMANANT = 1;
    private static final Integer UPDATED_NBRE_EMPLOYEE_PERMANANT = 2;

    private static final String DEFAULT_SPECIALITE_PRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE_PRODUCTION = "BBBBBBBBBB";

    @Autowired
    private AgriculteurRepository agriculteurRepository;

    @Autowired
    private AgriculteurSearchRepository agriculteurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgriculteurMockMvc;

    private Agriculteur agriculteur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgriculteurResource agriculteurResource = new AgriculteurResource(agriculteurRepository, agriculteurSearchRepository);
        this.restAgriculteurMockMvc = MockMvcBuilders.standaloneSetup(agriculteurResource)
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
    public static Agriculteur createEntity(EntityManager em) {
        Agriculteur agriculteur = new Agriculteur()
            .typeSociete(DEFAULT_TYPE_SOCIETE)
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .adresse(DEFAULT_ADRESSE)
            .adresseLivraison(DEFAULT_ADRESSE_LIVRAISON)
            .telephone(DEFAULT_TELEPHONE)
            .typeAgricole(DEFAULT_TYPE_AGRICOLE)
            .superficie(DEFAULT_SUPERFICIE)
            .superficieIrriguee(DEFAULT_SUPERFICIE_IRRIGUEE)
            .bio(DEFAULT_BIO)
            .nbreEmployeePermanant(DEFAULT_NBRE_EMPLOYEE_PERMANANT)
            .specialiteProduction(DEFAULT_SPECIALITE_PRODUCTION);
        return agriculteur;
    }

    @Before
    public void initTest() {
        agriculteurSearchRepository.deleteAll();
        agriculteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgriculteur() throws Exception {
        int databaseSizeBeforeCreate = agriculteurRepository.findAll().size();

        // Create the Agriculteur
        restAgriculteurMockMvc.perform(post("/api/agriculteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agriculteur)))
            .andExpect(status().isCreated());

        // Validate the Agriculteur in the database
        List<Agriculteur> agriculteurList = agriculteurRepository.findAll();
        assertThat(agriculteurList).hasSize(databaseSizeBeforeCreate + 1);
        Agriculteur testAgriculteur = agriculteurList.get(agriculteurList.size() - 1);
        assertThat(testAgriculteur.getTypeSociete()).isEqualTo(DEFAULT_TYPE_SOCIETE);
        assertThat(testAgriculteur.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testAgriculteur.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAgriculteur.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAgriculteur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAgriculteur.getAdresseLivraison()).isEqualTo(DEFAULT_ADRESSE_LIVRAISON);
        assertThat(testAgriculteur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAgriculteur.getTypeAgricole()).isEqualTo(DEFAULT_TYPE_AGRICOLE);
        assertThat(testAgriculteur.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
        assertThat(testAgriculteur.getSuperficieIrriguee()).isEqualTo(DEFAULT_SUPERFICIE_IRRIGUEE);
        assertThat(testAgriculteur.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testAgriculteur.getNbreEmployeePermanant()).isEqualTo(DEFAULT_NBRE_EMPLOYEE_PERMANANT);
        assertThat(testAgriculteur.getSpecialiteProduction()).isEqualTo(DEFAULT_SPECIALITE_PRODUCTION);

        // Validate the Agriculteur in Elasticsearch
        Agriculteur agriculteurEs = agriculteurSearchRepository.findOne(testAgriculteur.getId());
        assertThat(agriculteurEs).isEqualToComparingFieldByField(testAgriculteur);
    }

    @Test
    @Transactional
    public void createAgriculteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agriculteurRepository.findAll().size();

        // Create the Agriculteur with an existing ID
        agriculteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgriculteurMockMvc.perform(post("/api/agriculteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agriculteur)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Agriculteur> agriculteurList = agriculteurRepository.findAll();
        assertThat(agriculteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAgriculteurs() throws Exception {
        // Initialize the database
        agriculteurRepository.saveAndFlush(agriculteur);

        // Get all the agriculteurList
        restAgriculteurMockMvc.perform(get("/api/agriculteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agriculteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeSociete").value(hasItem(DEFAULT_TYPE_SOCIETE.toString())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].typeAgricole").value(hasItem(DEFAULT_TYPE_AGRICOLE.toString())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE.doubleValue())))
            .andExpect(jsonPath("$.[*].superficieIrriguee").value(hasItem(DEFAULT_SUPERFICIE_IRRIGUEE.doubleValue())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].nbreEmployeePermanant").value(hasItem(DEFAULT_NBRE_EMPLOYEE_PERMANANT)))
            .andExpect(jsonPath("$.[*].specialiteProduction").value(hasItem(DEFAULT_SPECIALITE_PRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getAgriculteur() throws Exception {
        // Initialize the database
        agriculteurRepository.saveAndFlush(agriculteur);

        // Get the agriculteur
        restAgriculteurMockMvc.perform(get("/api/agriculteurs/{id}", agriculteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agriculteur.getId().intValue()))
            .andExpect(jsonPath("$.typeSociete").value(DEFAULT_TYPE_SOCIETE.toString()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.adresseLivraison").value(DEFAULT_ADRESSE_LIVRAISON.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.typeAgricole").value(DEFAULT_TYPE_AGRICOLE.toString()))
            .andExpect(jsonPath("$.superficie").value(DEFAULT_SUPERFICIE.doubleValue()))
            .andExpect(jsonPath("$.superficieIrriguee").value(DEFAULT_SUPERFICIE_IRRIGUEE.doubleValue()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO))
            .andExpect(jsonPath("$.nbreEmployeePermanant").value(DEFAULT_NBRE_EMPLOYEE_PERMANANT))
            .andExpect(jsonPath("$.specialiteProduction").value(DEFAULT_SPECIALITE_PRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgriculteur() throws Exception {
        // Get the agriculteur
        restAgriculteurMockMvc.perform(get("/api/agriculteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgriculteur() throws Exception {
        // Initialize the database
        agriculteurRepository.saveAndFlush(agriculteur);
        agriculteurSearchRepository.save(agriculteur);
        int databaseSizeBeforeUpdate = agriculteurRepository.findAll().size();

        // Update the agriculteur
        Agriculteur updatedAgriculteur = agriculteurRepository.findOne(agriculteur.getId());
        updatedAgriculteur
            .typeSociete(UPDATED_TYPE_SOCIETE)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .adresse(UPDATED_ADRESSE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
            .telephone(UPDATED_TELEPHONE)
            .typeAgricole(UPDATED_TYPE_AGRICOLE)
            .superficie(UPDATED_SUPERFICIE)
            .superficieIrriguee(UPDATED_SUPERFICIE_IRRIGUEE)
            .bio(UPDATED_BIO)
            .nbreEmployeePermanant(UPDATED_NBRE_EMPLOYEE_PERMANANT)
            .specialiteProduction(UPDATED_SPECIALITE_PRODUCTION);

        restAgriculteurMockMvc.perform(put("/api/agriculteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgriculteur)))
            .andExpect(status().isOk());

        // Validate the Agriculteur in the database
        List<Agriculteur> agriculteurList = agriculteurRepository.findAll();
        assertThat(agriculteurList).hasSize(databaseSizeBeforeUpdate);
        Agriculteur testAgriculteur = agriculteurList.get(agriculteurList.size() - 1);
        assertThat(testAgriculteur.getTypeSociete()).isEqualTo(UPDATED_TYPE_SOCIETE);
        assertThat(testAgriculteur.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testAgriculteur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAgriculteur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAgriculteur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAgriculteur.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
        assertThat(testAgriculteur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAgriculteur.getTypeAgricole()).isEqualTo(UPDATED_TYPE_AGRICOLE);
        assertThat(testAgriculteur.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
        assertThat(testAgriculteur.getSuperficieIrriguee()).isEqualTo(UPDATED_SUPERFICIE_IRRIGUEE);
        assertThat(testAgriculteur.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testAgriculteur.getNbreEmployeePermanant()).isEqualTo(UPDATED_NBRE_EMPLOYEE_PERMANANT);
        assertThat(testAgriculteur.getSpecialiteProduction()).isEqualTo(UPDATED_SPECIALITE_PRODUCTION);

        // Validate the Agriculteur in Elasticsearch
        Agriculteur agriculteurEs = agriculteurSearchRepository.findOne(testAgriculteur.getId());
        assertThat(agriculteurEs).isEqualToComparingFieldByField(testAgriculteur);
    }

    @Test
    @Transactional
    public void updateNonExistingAgriculteur() throws Exception {
        int databaseSizeBeforeUpdate = agriculteurRepository.findAll().size();

        // Create the Agriculteur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgriculteurMockMvc.perform(put("/api/agriculteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agriculteur)))
            .andExpect(status().isCreated());

        // Validate the Agriculteur in the database
        List<Agriculteur> agriculteurList = agriculteurRepository.findAll();
        assertThat(agriculteurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgriculteur() throws Exception {
        // Initialize the database
        agriculteurRepository.saveAndFlush(agriculteur);
        agriculteurSearchRepository.save(agriculteur);
        int databaseSizeBeforeDelete = agriculteurRepository.findAll().size();

        // Get the agriculteur
        restAgriculteurMockMvc.perform(delete("/api/agriculteurs/{id}", agriculteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean agriculteurExistsInEs = agriculteurSearchRepository.exists(agriculteur.getId());
        assertThat(agriculteurExistsInEs).isFalse();

        // Validate the database is empty
        List<Agriculteur> agriculteurList = agriculteurRepository.findAll();
        assertThat(agriculteurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAgriculteur() throws Exception {
        // Initialize the database
        agriculteurRepository.saveAndFlush(agriculteur);
        agriculteurSearchRepository.save(agriculteur);

        // Search the agriculteur
        restAgriculteurMockMvc.perform(get("/api/_search/agriculteurs?query=id:" + agriculteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agriculteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeSociete").value(hasItem(DEFAULT_TYPE_SOCIETE.toString())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].typeAgricole").value(hasItem(DEFAULT_TYPE_AGRICOLE.toString())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE.doubleValue())))
            .andExpect(jsonPath("$.[*].superficieIrriguee").value(hasItem(DEFAULT_SUPERFICIE_IRRIGUEE.doubleValue())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO)))
            .andExpect(jsonPath("$.[*].nbreEmployeePermanant").value(hasItem(DEFAULT_NBRE_EMPLOYEE_PERMANANT)))
            .andExpect(jsonPath("$.[*].specialiteProduction").value(hasItem(DEFAULT_SPECIALITE_PRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agriculteur.class);
    }
}

package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.EmpAnnonce;
import com.agrobourse.dev.repository.EmpAnnonceRepository;
import com.agrobourse.dev.repository.search.EmpAnnonceSearchRepository;
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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmpAnnonceResource REST controller.
 *
 * @see EmpAnnonceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class EmpAnnonceResourceIntTest {

    private static final LocalDate DEFAULT_INIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CONTRAT = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_VALIDATION = 1L;
    private static final Long UPDATED_VALIDATION = 2L;

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NBREDEPOSTES = 1;
    private static final Integer UPDATED_NBREDEPOSTES = 2;

    @Autowired
    private EmpAnnonceRepository empAnnonceRepository;

    @Autowired
    private EmpAnnonceSearchRepository empAnnonceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpAnnonceMockMvc;

    private EmpAnnonce empAnnonce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpAnnonceResource empAnnonceResource = new EmpAnnonceResource(empAnnonceRepository, empAnnonceSearchRepository);
        this.restEmpAnnonceMockMvc = MockMvcBuilders.standaloneSetup(empAnnonceResource)
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
    public static EmpAnnonce createEntity(EntityManager em) {
        EmpAnnonce empAnnonce = new EmpAnnonce()
            .initDate(DEFAULT_INIT_DATE)
            .finDate(DEFAULT_FIN_DATE)
            .title(DEFAULT_TITLE)
            .typeContrat(DEFAULT_TYPE_CONTRAT)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .validation(DEFAULT_VALIDATION)
            .adresse(DEFAULT_ADRESSE)
            .nbredepostes(DEFAULT_NBREDEPOSTES);
        return empAnnonce;
    }

    @Before
    public void initTest() {
        empAnnonceSearchRepository.deleteAll();
        empAnnonce = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpAnnonce() throws Exception {
        int databaseSizeBeforeCreate = empAnnonceRepository.findAll().size();

        // Create the EmpAnnonce
        restEmpAnnonceMockMvc.perform(post("/api/emp-annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empAnnonce)))
            .andExpect(status().isCreated());

        // Validate the EmpAnnonce in the database
        List<EmpAnnonce> empAnnonceList = empAnnonceRepository.findAll();
        assertThat(empAnnonceList).hasSize(databaseSizeBeforeCreate + 1);
        EmpAnnonce testEmpAnnonce = empAnnonceList.get(empAnnonceList.size() - 1);
        assertThat(testEmpAnnonce.getInitDate()).isEqualTo(DEFAULT_INIT_DATE);
        assertThat(testEmpAnnonce.getFinDate()).isEqualTo(DEFAULT_FIN_DATE);
        assertThat(testEmpAnnonce.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmpAnnonce.getTypeContrat()).isEqualTo(DEFAULT_TYPE_CONTRAT);
        assertThat(testEmpAnnonce.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testEmpAnnonce.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testEmpAnnonce.getValidation()).isEqualTo(DEFAULT_VALIDATION);
        assertThat(testEmpAnnonce.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEmpAnnonce.getNbredepostes()).isEqualTo(DEFAULT_NBREDEPOSTES);

        // Validate the EmpAnnonce in Elasticsearch
        EmpAnnonce empAnnonceEs = empAnnonceSearchRepository.findOne(testEmpAnnonce.getId());
        assertThat(empAnnonceEs).isEqualToComparingFieldByField(testEmpAnnonce);
    }

    @Test
    @Transactional
    public void createEmpAnnonceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empAnnonceRepository.findAll().size();

        // Create the EmpAnnonce with an existing ID
        empAnnonce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpAnnonceMockMvc.perform(post("/api/emp-annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empAnnonce)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmpAnnonce> empAnnonceList = empAnnonceRepository.findAll();
        assertThat(empAnnonceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmpAnnonces() throws Exception {
        // Initialize the database
        empAnnonceRepository.saveAndFlush(empAnnonce);

        // Get all the empAnnonceList
        restEmpAnnonceMockMvc.perform(get("/api/emp-annonces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empAnnonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].finDate").value(hasItem(DEFAULT_FIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].typeContrat").value(hasItem(DEFAULT_TYPE_CONTRAT.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION.intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].nbredepostes").value(hasItem(DEFAULT_NBREDEPOSTES)));
    }

    @Test
    @Transactional
    public void getEmpAnnonce() throws Exception {
        // Initialize the database
        empAnnonceRepository.saveAndFlush(empAnnonce);

        // Get the empAnnonce
        restEmpAnnonceMockMvc.perform(get("/api/emp-annonces/{id}", empAnnonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empAnnonce.getId().intValue()))
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE.toString()))
            .andExpect(jsonPath("$.finDate").value(DEFAULT_FIN_DATE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.typeContrat").value(DEFAULT_TYPE_CONTRAT.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.validation").value(DEFAULT_VALIDATION.intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.nbredepostes").value(DEFAULT_NBREDEPOSTES));
    }

    @Test
    @Transactional
    public void getNonExistingEmpAnnonce() throws Exception {
        // Get the empAnnonce
        restEmpAnnonceMockMvc.perform(get("/api/emp-annonces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpAnnonce() throws Exception {
        // Initialize the database
        empAnnonceRepository.saveAndFlush(empAnnonce);
        empAnnonceSearchRepository.save(empAnnonce);
        int databaseSizeBeforeUpdate = empAnnonceRepository.findAll().size();

        // Update the empAnnonce
        EmpAnnonce updatedEmpAnnonce = empAnnonceRepository.findOne(empAnnonce.getId());
        updatedEmpAnnonce
            .initDate(UPDATED_INIT_DATE)
            .finDate(UPDATED_FIN_DATE)
            .title(UPDATED_TITLE)
            .typeContrat(UPDATED_TYPE_CONTRAT)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .validation(UPDATED_VALIDATION)
            .adresse(UPDATED_ADRESSE)
            .nbredepostes(UPDATED_NBREDEPOSTES);

        restEmpAnnonceMockMvc.perform(put("/api/emp-annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpAnnonce)))
            .andExpect(status().isOk());

        // Validate the EmpAnnonce in the database
        List<EmpAnnonce> empAnnonceList = empAnnonceRepository.findAll();
        assertThat(empAnnonceList).hasSize(databaseSizeBeforeUpdate);
        EmpAnnonce testEmpAnnonce = empAnnonceList.get(empAnnonceList.size() - 1);
        assertThat(testEmpAnnonce.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testEmpAnnonce.getFinDate()).isEqualTo(UPDATED_FIN_DATE);
        assertThat(testEmpAnnonce.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmpAnnonce.getTypeContrat()).isEqualTo(UPDATED_TYPE_CONTRAT);
        assertThat(testEmpAnnonce.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testEmpAnnonce.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testEmpAnnonce.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testEmpAnnonce.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmpAnnonce.getNbredepostes()).isEqualTo(UPDATED_NBREDEPOSTES);

        // Validate the EmpAnnonce in Elasticsearch
        EmpAnnonce empAnnonceEs = empAnnonceSearchRepository.findOne(testEmpAnnonce.getId());
        assertThat(empAnnonceEs).isEqualToComparingFieldByField(testEmpAnnonce);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = empAnnonceRepository.findAll().size();

        // Create the EmpAnnonce

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmpAnnonceMockMvc.perform(put("/api/emp-annonces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empAnnonce)))
            .andExpect(status().isCreated());

        // Validate the EmpAnnonce in the database
        List<EmpAnnonce> empAnnonceList = empAnnonceRepository.findAll();
        assertThat(empAnnonceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmpAnnonce() throws Exception {
        // Initialize the database
        empAnnonceRepository.saveAndFlush(empAnnonce);
        empAnnonceSearchRepository.save(empAnnonce);
        int databaseSizeBeforeDelete = empAnnonceRepository.findAll().size();

        // Get the empAnnonce
        restEmpAnnonceMockMvc.perform(delete("/api/emp-annonces/{id}", empAnnonce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean empAnnonceExistsInEs = empAnnonceSearchRepository.exists(empAnnonce.getId());
        assertThat(empAnnonceExistsInEs).isFalse();

        // Validate the database is empty
        List<EmpAnnonce> empAnnonceList = empAnnonceRepository.findAll();
        assertThat(empAnnonceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmpAnnonce() throws Exception {
        // Initialize the database
        empAnnonceRepository.saveAndFlush(empAnnonce);
        empAnnonceSearchRepository.save(empAnnonce);

        // Search the empAnnonce
        restEmpAnnonceMockMvc.perform(get("/api/_search/emp-annonces?query=id:" + empAnnonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empAnnonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].finDate").value(hasItem(DEFAULT_FIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].typeContrat").value(hasItem(DEFAULT_TYPE_CONTRAT.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION.intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].nbredepostes").value(hasItem(DEFAULT_NBREDEPOSTES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpAnnonce.class);
    }
}

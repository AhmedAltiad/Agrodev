package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.EmpActualite;
import com.agrobourse.dev.repository.EmpActualiteRepository;
import com.agrobourse.dev.repository.search.EmpActualiteSearchRepository;
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

import com.agrobourse.dev.domain.enumeration.Empactualitetype;
/**
 * Test class for the EmpActualiteResource REST controller.
 *
 * @see EmpActualiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class EmpActualiteResourceIntTest {

    private static final LocalDate DEFAULT_INIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PERIODE = "AAAAAAAAAA";
    private static final String UPDATED_PERIODE = "BBBBBBBBBB";

    private static final String DEFAULT_SECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_SECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DISC = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DISC = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DISC = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DISC = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Empactualitetype DEFAULT_EMPACTTYPE = Empactualitetype.FETES;
    private static final Empactualitetype UPDATED_EMPACTTYPE = Empactualitetype.FOIRE;

    @Autowired
    private EmpActualiteRepository empActualiteRepository;

    @Autowired
    private EmpActualiteSearchRepository empActualiteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpActualiteMockMvc;

    private EmpActualite empActualite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpActualiteResource empActualiteResource = new EmpActualiteResource(empActualiteRepository, empActualiteSearchRepository);
        this.restEmpActualiteMockMvc = MockMvcBuilders.standaloneSetup(empActualiteResource)
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
    public static EmpActualite createEntity(EntityManager em) {
        EmpActualite empActualite = new EmpActualite()
            .initDate(DEFAULT_INIT_DATE)
            .periode(DEFAULT_PERIODE)
            .secteur(DEFAULT_SECTEUR)
            .title(DEFAULT_TITLE)
            .shortDisc(DEFAULT_SHORT_DISC)
            .longDisc(DEFAULT_LONG_DISC)
            .image(DEFAULT_IMAGE)
            .adresse(DEFAULT_ADRESSE)
            .empacttype(DEFAULT_EMPACTTYPE);
        return empActualite;
    }

    @Before
    public void initTest() {
        empActualiteSearchRepository.deleteAll();
        empActualite = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpActualite() throws Exception {
        int databaseSizeBeforeCreate = empActualiteRepository.findAll().size();

        // Create the EmpActualite
        restEmpActualiteMockMvc.perform(post("/api/emp-actualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empActualite)))
            .andExpect(status().isCreated());

        // Validate the EmpActualite in the database
        List<EmpActualite> empActualiteList = empActualiteRepository.findAll();
        assertThat(empActualiteList).hasSize(databaseSizeBeforeCreate + 1);
        EmpActualite testEmpActualite = empActualiteList.get(empActualiteList.size() - 1);
        assertThat(testEmpActualite.getInitDate()).isEqualTo(DEFAULT_INIT_DATE);
        assertThat(testEmpActualite.getPeriode()).isEqualTo(DEFAULT_PERIODE);
        assertThat(testEmpActualite.getSecteur()).isEqualTo(DEFAULT_SECTEUR);
        assertThat(testEmpActualite.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmpActualite.getShortDisc()).isEqualTo(DEFAULT_SHORT_DISC);
        assertThat(testEmpActualite.getLongDisc()).isEqualTo(DEFAULT_LONG_DISC);
        assertThat(testEmpActualite.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testEmpActualite.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEmpActualite.getEmpacttype()).isEqualTo(DEFAULT_EMPACTTYPE);

        // Validate the EmpActualite in Elasticsearch
        EmpActualite empActualiteEs = empActualiteSearchRepository.findOne(testEmpActualite.getId());
        assertThat(empActualiteEs).isEqualToComparingFieldByField(testEmpActualite);
    }

    @Test
    @Transactional
    public void createEmpActualiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empActualiteRepository.findAll().size();

        // Create the EmpActualite with an existing ID
        empActualite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpActualiteMockMvc.perform(post("/api/emp-actualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empActualite)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmpActualite> empActualiteList = empActualiteRepository.findAll();
        assertThat(empActualiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmpActualites() throws Exception {
        // Initialize the database
        empActualiteRepository.saveAndFlush(empActualite);

        // Get all the empActualiteList
        restEmpActualiteMockMvc.perform(get("/api/emp-actualites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empActualite.getId().intValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].periode").value(hasItem(DEFAULT_PERIODE.toString())))
            .andExpect(jsonPath("$.[*].secteur").value(hasItem(DEFAULT_SECTEUR.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shortDisc").value(hasItem(DEFAULT_SHORT_DISC.toString())))
            .andExpect(jsonPath("$.[*].longDisc").value(hasItem(DEFAULT_LONG_DISC.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].empacttype").value(hasItem(DEFAULT_EMPACTTYPE.toString())));
    }

    @Test
    @Transactional
    public void getEmpActualite() throws Exception {
        // Initialize the database
        empActualiteRepository.saveAndFlush(empActualite);

        // Get the empActualite
        restEmpActualiteMockMvc.perform(get("/api/emp-actualites/{id}", empActualite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empActualite.getId().intValue()))
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE.toString()))
            .andExpect(jsonPath("$.periode").value(DEFAULT_PERIODE.toString()))
            .andExpect(jsonPath("$.secteur").value(DEFAULT_SECTEUR.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.shortDisc").value(DEFAULT_SHORT_DISC.toString()))
            .andExpect(jsonPath("$.longDisc").value(DEFAULT_LONG_DISC.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.empacttype").value(DEFAULT_EMPACTTYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpActualite() throws Exception {
        // Get the empActualite
        restEmpActualiteMockMvc.perform(get("/api/emp-actualites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpActualite() throws Exception {
        // Initialize the database
        empActualiteRepository.saveAndFlush(empActualite);
        empActualiteSearchRepository.save(empActualite);
        int databaseSizeBeforeUpdate = empActualiteRepository.findAll().size();

        // Update the empActualite
        EmpActualite updatedEmpActualite = empActualiteRepository.findOne(empActualite.getId());
        updatedEmpActualite
            .initDate(UPDATED_INIT_DATE)
            .periode(UPDATED_PERIODE)
            .secteur(UPDATED_SECTEUR)
            .title(UPDATED_TITLE)
            .shortDisc(UPDATED_SHORT_DISC)
            .longDisc(UPDATED_LONG_DISC)
            .image(UPDATED_IMAGE)
            .adresse(UPDATED_ADRESSE)
            .empacttype(UPDATED_EMPACTTYPE);

        restEmpActualiteMockMvc.perform(put("/api/emp-actualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpActualite)))
            .andExpect(status().isOk());

        // Validate the EmpActualite in the database
        List<EmpActualite> empActualiteList = empActualiteRepository.findAll();
        assertThat(empActualiteList).hasSize(databaseSizeBeforeUpdate);
        EmpActualite testEmpActualite = empActualiteList.get(empActualiteList.size() - 1);
        assertThat(testEmpActualite.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testEmpActualite.getPeriode()).isEqualTo(UPDATED_PERIODE);
        assertThat(testEmpActualite.getSecteur()).isEqualTo(UPDATED_SECTEUR);
        assertThat(testEmpActualite.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmpActualite.getShortDisc()).isEqualTo(UPDATED_SHORT_DISC);
        assertThat(testEmpActualite.getLongDisc()).isEqualTo(UPDATED_LONG_DISC);
        assertThat(testEmpActualite.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testEmpActualite.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmpActualite.getEmpacttype()).isEqualTo(UPDATED_EMPACTTYPE);

        // Validate the EmpActualite in Elasticsearch
        EmpActualite empActualiteEs = empActualiteSearchRepository.findOne(testEmpActualite.getId());
        assertThat(empActualiteEs).isEqualToComparingFieldByField(testEmpActualite);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpActualite() throws Exception {
        int databaseSizeBeforeUpdate = empActualiteRepository.findAll().size();

        // Create the EmpActualite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmpActualiteMockMvc.perform(put("/api/emp-actualites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empActualite)))
            .andExpect(status().isCreated());

        // Validate the EmpActualite in the database
        List<EmpActualite> empActualiteList = empActualiteRepository.findAll();
        assertThat(empActualiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmpActualite() throws Exception {
        // Initialize the database
        empActualiteRepository.saveAndFlush(empActualite);
        empActualiteSearchRepository.save(empActualite);
        int databaseSizeBeforeDelete = empActualiteRepository.findAll().size();

        // Get the empActualite
        restEmpActualiteMockMvc.perform(delete("/api/emp-actualites/{id}", empActualite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean empActualiteExistsInEs = empActualiteSearchRepository.exists(empActualite.getId());
        assertThat(empActualiteExistsInEs).isFalse();

        // Validate the database is empty
        List<EmpActualite> empActualiteList = empActualiteRepository.findAll();
        assertThat(empActualiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmpActualite() throws Exception {
        // Initialize the database
        empActualiteRepository.saveAndFlush(empActualite);
        empActualiteSearchRepository.save(empActualite);

        // Search the empActualite
        restEmpActualiteMockMvc.perform(get("/api/_search/emp-actualites?query=id:" + empActualite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empActualite.getId().intValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].periode").value(hasItem(DEFAULT_PERIODE.toString())))
            .andExpect(jsonPath("$.[*].secteur").value(hasItem(DEFAULT_SECTEUR.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shortDisc").value(hasItem(DEFAULT_SHORT_DISC.toString())))
            .andExpect(jsonPath("$.[*].longDisc").value(hasItem(DEFAULT_LONG_DISC.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].empacttype").value(hasItem(DEFAULT_EMPACTTYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpActualite.class);
    }
}

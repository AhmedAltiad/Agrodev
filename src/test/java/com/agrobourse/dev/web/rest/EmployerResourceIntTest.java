package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Employer;
import com.agrobourse.dev.repository.EmployerRepository;
import com.agrobourse.dev.repository.search.EmployerSearchRepository;
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
 * Test class for the EmployerResource REST controller.
 *
 * @see EmployerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class EmployerResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_SIEGE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NBRE_EMPLOYEE_PERMANANT = 1;
    private static final Integer UPDATED_NBRE_EMPLOYEE_PERMANANT = 2;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EmployerSearchRepository employerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployerMockMvc;

    private Employer employer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployerResource employerResource = new EmployerResource(employerRepository, employerSearchRepository);
        this.restEmployerMockMvc = MockMvcBuilders.standaloneSetup(employerResource)
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
    public static Employer createEntity(EntityManager em) {
        Employer employer = new Employer()
            .companyName(DEFAULT_COMPANY_NAME)
            .type(DEFAULT_TYPE)
            .companyDescription(DEFAULT_COMPANY_DESCRIPTION)
            .adresseSiege(DEFAULT_ADRESSE_SIEGE)
            .telephone(DEFAULT_TELEPHONE)
            .nbreEmployeePermanant(DEFAULT_NBRE_EMPLOYEE_PERMANANT);
        return employer;
    }

    @Before
    public void initTest() {
        employerSearchRepository.deleteAll();
        employer = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployer() throws Exception {
        int databaseSizeBeforeCreate = employerRepository.findAll().size();

        // Create the Employer
        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isCreated());

        // Validate the Employer in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeCreate + 1);
        Employer testEmployer = employerList.get(employerList.size() - 1);
        assertThat(testEmployer.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testEmployer.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployer.getCompanyDescription()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION);
        assertThat(testEmployer.getAdresseSiege()).isEqualTo(DEFAULT_ADRESSE_SIEGE);
        assertThat(testEmployer.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmployer.getNbreEmployeePermanant()).isEqualTo(DEFAULT_NBRE_EMPLOYEE_PERMANANT);

        // Validate the Employer in Elasticsearch
        Employer employerEs = employerSearchRepository.findOne(testEmployer.getId());
        assertThat(employerEs).isEqualToComparingFieldByField(testEmployer);
    }

    @Test
    @Transactional
    public void createEmployerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employerRepository.findAll().size();

        // Create the Employer with an existing ID
        employer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployerMockMvc.perform(post("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployers() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

        // Get all the employerList
        restEmployerMockMvc.perform(get("/api/employers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employer.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].adresseSiege").value(hasItem(DEFAULT_ADRESSE_SIEGE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].nbreEmployeePermanant").value(hasItem(DEFAULT_NBRE_EMPLOYEE_PERMANANT)));
    }

    @Test
    @Transactional
    public void getEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);

        // Get the employer
        restEmployerMockMvc.perform(get("/api/employers/{id}", employer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employer.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.companyDescription").value(DEFAULT_COMPANY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.adresseSiege").value(DEFAULT_ADRESSE_SIEGE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.nbreEmployeePermanant").value(DEFAULT_NBRE_EMPLOYEE_PERMANANT));
    }

    @Test
    @Transactional
    public void getNonExistingEmployer() throws Exception {
        // Get the employer
        restEmployerMockMvc.perform(get("/api/employers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);
        employerSearchRepository.save(employer);
        int databaseSizeBeforeUpdate = employerRepository.findAll().size();

        // Update the employer
        Employer updatedEmployer = employerRepository.findOne(employer.getId());
        updatedEmployer
            .companyName(UPDATED_COMPANY_NAME)
            .type(UPDATED_TYPE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .adresseSiege(UPDATED_ADRESSE_SIEGE)
            .telephone(UPDATED_TELEPHONE)
            .nbreEmployeePermanant(UPDATED_NBRE_EMPLOYEE_PERMANANT);

        restEmployerMockMvc.perform(put("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployer)))
            .andExpect(status().isOk());

        // Validate the Employer in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeUpdate);
        Employer testEmployer = employerList.get(employerList.size() - 1);
        assertThat(testEmployer.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployer.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployer.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testEmployer.getAdresseSiege()).isEqualTo(UPDATED_ADRESSE_SIEGE);
        assertThat(testEmployer.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmployer.getNbreEmployeePermanant()).isEqualTo(UPDATED_NBRE_EMPLOYEE_PERMANANT);

        // Validate the Employer in Elasticsearch
        Employer employerEs = employerSearchRepository.findOne(testEmployer.getId());
        assertThat(employerEs).isEqualToComparingFieldByField(testEmployer);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployer() throws Exception {
        int databaseSizeBeforeUpdate = employerRepository.findAll().size();

        // Create the Employer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployerMockMvc.perform(put("/api/employers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employer)))
            .andExpect(status().isCreated());

        // Validate the Employer in the database
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);
        employerSearchRepository.save(employer);
        int databaseSizeBeforeDelete = employerRepository.findAll().size();

        // Get the employer
        restEmployerMockMvc.perform(delete("/api/employers/{id}", employer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean employerExistsInEs = employerSearchRepository.exists(employer.getId());
        assertThat(employerExistsInEs).isFalse();

        // Validate the database is empty
        List<Employer> employerList = employerRepository.findAll();
        assertThat(employerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmployer() throws Exception {
        // Initialize the database
        employerRepository.saveAndFlush(employer);
        employerSearchRepository.save(employer);

        // Search the employer
        restEmployerMockMvc.perform(get("/api/_search/employers?query=id:" + employer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employer.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].adresseSiege").value(hasItem(DEFAULT_ADRESSE_SIEGE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].nbreEmployeePermanant").value(hasItem(DEFAULT_NBRE_EMPLOYEE_PERMANANT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employer.class);
    }
}

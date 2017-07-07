package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.EmpCV;
import com.agrobourse.dev.repository.EmpCVRepository;
import com.agrobourse.dev.repository.search.EmpCVSearchRepository;
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
 * Test class for the EmpCVResource REST controller.
 *
 * @see EmpCVResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class EmpCVResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ANNEEXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_ANNEEXPERIENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERMIS = 1;
    private static final Integer UPDATED_PERMIS = 2;

    private static final Integer DEFAULT_NIVEAU_SCOLAIRE = 1;
    private static final Integer UPDATED_NIVEAU_SCOLAIRE = 2;

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    @Autowired
    private EmpCVRepository empCVRepository;

    @Autowired
    private EmpCVSearchRepository empCVSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpCVMockMvc;

    private EmpCV empCV;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpCVResource empCVResource = new EmpCVResource(empCVRepository, empCVSearchRepository);
        this.restEmpCVMockMvc = MockMvcBuilders.standaloneSetup(empCVResource)
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
    public static EmpCV createEntity(EntityManager em) {
        EmpCV empCV = new EmpCV()
            .title(DEFAULT_TITLE)
            .anneexperience(DEFAULT_ANNEEXPERIENCE)
            .permis(DEFAULT_PERMIS)
            .niveauScolaire(DEFAULT_NIVEAU_SCOLAIRE)
            .diplome(DEFAULT_DIPLOME);
        return empCV;
    }

    @Before
    public void initTest() {
        empCVSearchRepository.deleteAll();
        empCV = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpCV() throws Exception {
        int databaseSizeBeforeCreate = empCVRepository.findAll().size();

        // Create the EmpCV
        restEmpCVMockMvc.perform(post("/api/emp-cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empCV)))
            .andExpect(status().isCreated());

        // Validate the EmpCV in the database
        List<EmpCV> empCVList = empCVRepository.findAll();
        assertThat(empCVList).hasSize(databaseSizeBeforeCreate + 1);
        EmpCV testEmpCV = empCVList.get(empCVList.size() - 1);
        assertThat(testEmpCV.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmpCV.getAnneexperience()).isEqualTo(DEFAULT_ANNEEXPERIENCE);
        assertThat(testEmpCV.getPermis()).isEqualTo(DEFAULT_PERMIS);
        assertThat(testEmpCV.getNiveauScolaire()).isEqualTo(DEFAULT_NIVEAU_SCOLAIRE);
        assertThat(testEmpCV.getDiplome()).isEqualTo(DEFAULT_DIPLOME);

        // Validate the EmpCV in Elasticsearch
        EmpCV empCVEs = empCVSearchRepository.findOne(testEmpCV.getId());
        assertThat(empCVEs).isEqualToComparingFieldByField(testEmpCV);
    }

    @Test
    @Transactional
    public void createEmpCVWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empCVRepository.findAll().size();

        // Create the EmpCV with an existing ID
        empCV.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpCVMockMvc.perform(post("/api/emp-cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empCV)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmpCV> empCVList = empCVRepository.findAll();
        assertThat(empCVList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmpCVS() throws Exception {
        // Initialize the database
        empCVRepository.saveAndFlush(empCV);

        // Get all the empCVList
        restEmpCVMockMvc.perform(get("/api/emp-cvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empCV.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].anneexperience").value(hasItem(DEFAULT_ANNEEXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].permis").value(hasItem(DEFAULT_PERMIS)))
            .andExpect(jsonPath("$.[*].niveauScolaire").value(hasItem(DEFAULT_NIVEAU_SCOLAIRE)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME.toString())));
    }

    @Test
    @Transactional
    public void getEmpCV() throws Exception {
        // Initialize the database
        empCVRepository.saveAndFlush(empCV);

        // Get the empCV
        restEmpCVMockMvc.perform(get("/api/emp-cvs/{id}", empCV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empCV.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.anneexperience").value(DEFAULT_ANNEEXPERIENCE.toString()))
            .andExpect(jsonPath("$.permis").value(DEFAULT_PERMIS))
            .andExpect(jsonPath("$.niveauScolaire").value(DEFAULT_NIVEAU_SCOLAIRE))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpCV() throws Exception {
        // Get the empCV
        restEmpCVMockMvc.perform(get("/api/emp-cvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpCV() throws Exception {
        // Initialize the database
        empCVRepository.saveAndFlush(empCV);
        empCVSearchRepository.save(empCV);
        int databaseSizeBeforeUpdate = empCVRepository.findAll().size();

        // Update the empCV
        EmpCV updatedEmpCV = empCVRepository.findOne(empCV.getId());
        updatedEmpCV
            .title(UPDATED_TITLE)
            .anneexperience(UPDATED_ANNEEXPERIENCE)
            .permis(UPDATED_PERMIS)
            .niveauScolaire(UPDATED_NIVEAU_SCOLAIRE)
            .diplome(UPDATED_DIPLOME);

        restEmpCVMockMvc.perform(put("/api/emp-cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpCV)))
            .andExpect(status().isOk());

        // Validate the EmpCV in the database
        List<EmpCV> empCVList = empCVRepository.findAll();
        assertThat(empCVList).hasSize(databaseSizeBeforeUpdate);
        EmpCV testEmpCV = empCVList.get(empCVList.size() - 1);
        assertThat(testEmpCV.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmpCV.getAnneexperience()).isEqualTo(UPDATED_ANNEEXPERIENCE);
        assertThat(testEmpCV.getPermis()).isEqualTo(UPDATED_PERMIS);
        assertThat(testEmpCV.getNiveauScolaire()).isEqualTo(UPDATED_NIVEAU_SCOLAIRE);
        assertThat(testEmpCV.getDiplome()).isEqualTo(UPDATED_DIPLOME);

        // Validate the EmpCV in Elasticsearch
        EmpCV empCVEs = empCVSearchRepository.findOne(testEmpCV.getId());
        assertThat(empCVEs).isEqualToComparingFieldByField(testEmpCV);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpCV() throws Exception {
        int databaseSizeBeforeUpdate = empCVRepository.findAll().size();

        // Create the EmpCV

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmpCVMockMvc.perform(put("/api/emp-cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empCV)))
            .andExpect(status().isCreated());

        // Validate the EmpCV in the database
        List<EmpCV> empCVList = empCVRepository.findAll();
        assertThat(empCVList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmpCV() throws Exception {
        // Initialize the database
        empCVRepository.saveAndFlush(empCV);
        empCVSearchRepository.save(empCV);
        int databaseSizeBeforeDelete = empCVRepository.findAll().size();

        // Get the empCV
        restEmpCVMockMvc.perform(delete("/api/emp-cvs/{id}", empCV.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean empCVExistsInEs = empCVSearchRepository.exists(empCV.getId());
        assertThat(empCVExistsInEs).isFalse();

        // Validate the database is empty
        List<EmpCV> empCVList = empCVRepository.findAll();
        assertThat(empCVList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmpCV() throws Exception {
        // Initialize the database
        empCVRepository.saveAndFlush(empCV);
        empCVSearchRepository.save(empCV);

        // Search the empCV
        restEmpCVMockMvc.perform(get("/api/_search/emp-cvs?query=id:" + empCV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empCV.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].anneexperience").value(hasItem(DEFAULT_ANNEEXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].permis").value(hasItem(DEFAULT_PERMIS)))
            .andExpect(jsonPath("$.[*].niveauScolaire").value(hasItem(DEFAULT_NIVEAU_SCOLAIRE)))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpCV.class);
    }
}

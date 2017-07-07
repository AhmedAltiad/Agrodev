package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Portefolio;
import com.agrobourse.dev.repository.PortefolioRepository;
import com.agrobourse.dev.repository.search.PortefolioSearchRepository;
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
 * Test class for the PortefolioResource REST controller.
 *
 * @see PortefolioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class PortefolioResourceIntTest {

    private static final Integer DEFAULT_NBREREALISATION = 1;
    private static final Integer UPDATED_NBREREALISATION = 2;

    private static final Integer DEFAULT_NBREENCOURS = 1;
    private static final Integer UPDATED_NBREENCOURS = 2;

    private static final String DEFAULT_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE = "BBBBBBBBBB";

    @Autowired
    private PortefolioRepository portefolioRepository;

    @Autowired
    private PortefolioSearchRepository portefolioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortefolioMockMvc;

    private Portefolio portefolio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PortefolioResource portefolioResource = new PortefolioResource(portefolioRepository, portefolioSearchRepository);
        this.restPortefolioMockMvc = MockMvcBuilders.standaloneSetup(portefolioResource)
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
    public static Portefolio createEntity(EntityManager em) {
        Portefolio portefolio = new Portefolio()
            .nbrerealisation(DEFAULT_NBREREALISATION)
            .nbreencours(DEFAULT_NBREENCOURS)
            .specialite(DEFAULT_SPECIALITE);
        return portefolio;
    }

    @Before
    public void initTest() {
        portefolioSearchRepository.deleteAll();
        portefolio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortefolio() throws Exception {
        int databaseSizeBeforeCreate = portefolioRepository.findAll().size();

        // Create the Portefolio
        restPortefolioMockMvc.perform(post("/api/portefolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portefolio)))
            .andExpect(status().isCreated());

        // Validate the Portefolio in the database
        List<Portefolio> portefolioList = portefolioRepository.findAll();
        assertThat(portefolioList).hasSize(databaseSizeBeforeCreate + 1);
        Portefolio testPortefolio = portefolioList.get(portefolioList.size() - 1);
        assertThat(testPortefolio.getNbrerealisation()).isEqualTo(DEFAULT_NBREREALISATION);
        assertThat(testPortefolio.getNbreencours()).isEqualTo(DEFAULT_NBREENCOURS);
        assertThat(testPortefolio.getSpecialite()).isEqualTo(DEFAULT_SPECIALITE);

        // Validate the Portefolio in Elasticsearch
        Portefolio portefolioEs = portefolioSearchRepository.findOne(testPortefolio.getId());
        assertThat(portefolioEs).isEqualToComparingFieldByField(testPortefolio);
    }

    @Test
    @Transactional
    public void createPortefolioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portefolioRepository.findAll().size();

        // Create the Portefolio with an existing ID
        portefolio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortefolioMockMvc.perform(post("/api/portefolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portefolio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Portefolio> portefolioList = portefolioRepository.findAll();
        assertThat(portefolioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPortefolios() throws Exception {
        // Initialize the database
        portefolioRepository.saveAndFlush(portefolio);

        // Get all the portefolioList
        restPortefolioMockMvc.perform(get("/api/portefolios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portefolio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbrerealisation").value(hasItem(DEFAULT_NBREREALISATION)))
            .andExpect(jsonPath("$.[*].nbreencours").value(hasItem(DEFAULT_NBREENCOURS)))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())));
    }

    @Test
    @Transactional
    public void getPortefolio() throws Exception {
        // Initialize the database
        portefolioRepository.saveAndFlush(portefolio);

        // Get the portefolio
        restPortefolioMockMvc.perform(get("/api/portefolios/{id}", portefolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portefolio.getId().intValue()))
            .andExpect(jsonPath("$.nbrerealisation").value(DEFAULT_NBREREALISATION))
            .andExpect(jsonPath("$.nbreencours").value(DEFAULT_NBREENCOURS))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPortefolio() throws Exception {
        // Get the portefolio
        restPortefolioMockMvc.perform(get("/api/portefolios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortefolio() throws Exception {
        // Initialize the database
        portefolioRepository.saveAndFlush(portefolio);
        portefolioSearchRepository.save(portefolio);
        int databaseSizeBeforeUpdate = portefolioRepository.findAll().size();

        // Update the portefolio
        Portefolio updatedPortefolio = portefolioRepository.findOne(portefolio.getId());
        updatedPortefolio
            .nbrerealisation(UPDATED_NBREREALISATION)
            .nbreencours(UPDATED_NBREENCOURS)
            .specialite(UPDATED_SPECIALITE);

        restPortefolioMockMvc.perform(put("/api/portefolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPortefolio)))
            .andExpect(status().isOk());

        // Validate the Portefolio in the database
        List<Portefolio> portefolioList = portefolioRepository.findAll();
        assertThat(portefolioList).hasSize(databaseSizeBeforeUpdate);
        Portefolio testPortefolio = portefolioList.get(portefolioList.size() - 1);
        assertThat(testPortefolio.getNbrerealisation()).isEqualTo(UPDATED_NBREREALISATION);
        assertThat(testPortefolio.getNbreencours()).isEqualTo(UPDATED_NBREENCOURS);
        assertThat(testPortefolio.getSpecialite()).isEqualTo(UPDATED_SPECIALITE);

        // Validate the Portefolio in Elasticsearch
        Portefolio portefolioEs = portefolioSearchRepository.findOne(testPortefolio.getId());
        assertThat(portefolioEs).isEqualToComparingFieldByField(testPortefolio);
    }

    @Test
    @Transactional
    public void updateNonExistingPortefolio() throws Exception {
        int databaseSizeBeforeUpdate = portefolioRepository.findAll().size();

        // Create the Portefolio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPortefolioMockMvc.perform(put("/api/portefolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portefolio)))
            .andExpect(status().isCreated());

        // Validate the Portefolio in the database
        List<Portefolio> portefolioList = portefolioRepository.findAll();
        assertThat(portefolioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePortefolio() throws Exception {
        // Initialize the database
        portefolioRepository.saveAndFlush(portefolio);
        portefolioSearchRepository.save(portefolio);
        int databaseSizeBeforeDelete = portefolioRepository.findAll().size();

        // Get the portefolio
        restPortefolioMockMvc.perform(delete("/api/portefolios/{id}", portefolio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean portefolioExistsInEs = portefolioSearchRepository.exists(portefolio.getId());
        assertThat(portefolioExistsInEs).isFalse();

        // Validate the database is empty
        List<Portefolio> portefolioList = portefolioRepository.findAll();
        assertThat(portefolioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPortefolio() throws Exception {
        // Initialize the database
        portefolioRepository.saveAndFlush(portefolio);
        portefolioSearchRepository.save(portefolio);

        // Search the portefolio
        restPortefolioMockMvc.perform(get("/api/_search/portefolios?query=id:" + portefolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portefolio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbrerealisation").value(hasItem(DEFAULT_NBREREALISATION)))
            .andExpect(jsonPath("$.[*].nbreencours").value(hasItem(DEFAULT_NBREENCOURS)))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Portefolio.class);
    }
}

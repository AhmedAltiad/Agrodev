package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Palier;
import com.agrobourse.dev.repository.PalierRepository;
import com.agrobourse.dev.repository.search.PalierSearchRepository;
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
 * Test class for the PalierResource REST controller.
 *
 * @see PalierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class PalierResourceIntTest {

    private static final LocalDate DEFAULT_DATEDEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEFIN = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NOMBRECONS = 1;
    private static final Integer UPDATED_NOMBRECONS = 2;

    private static final Double DEFAULT_PROMOTION = 1D;
    private static final Double UPDATED_PROMOTION = 2D;

    @Autowired
    private PalierRepository palierRepository;

    @Autowired
    private PalierSearchRepository palierSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPalierMockMvc;

    private Palier palier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PalierResource palierResource = new PalierResource(palierRepository, palierSearchRepository);
        this.restPalierMockMvc = MockMvcBuilders.standaloneSetup(palierResource)
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
    public static Palier createEntity(EntityManager em) {
        Palier palier = new Palier()
            .datedebut(DEFAULT_DATEDEBUT)
            .datefin(DEFAULT_DATEFIN)
            .nombrecons(DEFAULT_NOMBRECONS)
            .promotion(DEFAULT_PROMOTION);
        return palier;
    }

    @Before
    public void initTest() {
        palierSearchRepository.deleteAll();
        palier = createEntity(em);
    }

    @Test
    @Transactional
    public void createPalier() throws Exception {
        int databaseSizeBeforeCreate = palierRepository.findAll().size();

        // Create the Palier
        restPalierMockMvc.perform(post("/api/paliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(palier)))
            .andExpect(status().isCreated());

        // Validate the Palier in the database
        List<Palier> palierList = palierRepository.findAll();
        assertThat(palierList).hasSize(databaseSizeBeforeCreate + 1);
        Palier testPalier = palierList.get(palierList.size() - 1);
        assertThat(testPalier.getDatedebut()).isEqualTo(DEFAULT_DATEDEBUT);
        assertThat(testPalier.getDatefin()).isEqualTo(DEFAULT_DATEFIN);
        assertThat(testPalier.getNombrecons()).isEqualTo(DEFAULT_NOMBRECONS);
        assertThat(testPalier.getPromotion()).isEqualTo(DEFAULT_PROMOTION);

        // Validate the Palier in Elasticsearch
        Palier palierEs = palierSearchRepository.findOne(testPalier.getId());
        assertThat(palierEs).isEqualToComparingFieldByField(testPalier);
    }

    @Test
    @Transactional
    public void createPalierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = palierRepository.findAll().size();

        // Create the Palier with an existing ID
        palier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPalierMockMvc.perform(post("/api/paliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(palier)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Palier> palierList = palierRepository.findAll();
        assertThat(palierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaliers() throws Exception {
        // Initialize the database
        palierRepository.saveAndFlush(palier);

        // Get all the palierList
        restPalierMockMvc.perform(get("/api/paliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palier.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT.toString())))
            .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN.toString())))
            .andExpect(jsonPath("$.[*].nombrecons").value(hasItem(DEFAULT_NOMBRECONS)))
            .andExpect(jsonPath("$.[*].promotion").value(hasItem(DEFAULT_PROMOTION.doubleValue())));
    }

    @Test
    @Transactional
    public void getPalier() throws Exception {
        // Initialize the database
        palierRepository.saveAndFlush(palier);

        // Get the palier
        restPalierMockMvc.perform(get("/api/paliers/{id}", palier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(palier.getId().intValue()))
            .andExpect(jsonPath("$.datedebut").value(DEFAULT_DATEDEBUT.toString()))
            .andExpect(jsonPath("$.datefin").value(DEFAULT_DATEFIN.toString()))
            .andExpect(jsonPath("$.nombrecons").value(DEFAULT_NOMBRECONS))
            .andExpect(jsonPath("$.promotion").value(DEFAULT_PROMOTION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPalier() throws Exception {
        // Get the palier
        restPalierMockMvc.perform(get("/api/paliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePalier() throws Exception {
        // Initialize the database
        palierRepository.saveAndFlush(palier);
        palierSearchRepository.save(palier);
        int databaseSizeBeforeUpdate = palierRepository.findAll().size();

        // Update the palier
        Palier updatedPalier = palierRepository.findOne(palier.getId());
        updatedPalier
            .datedebut(UPDATED_DATEDEBUT)
            .datefin(UPDATED_DATEFIN)
            .nombrecons(UPDATED_NOMBRECONS)
            .promotion(UPDATED_PROMOTION);

        restPalierMockMvc.perform(put("/api/paliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPalier)))
            .andExpect(status().isOk());

        // Validate the Palier in the database
        List<Palier> palierList = palierRepository.findAll();
        assertThat(palierList).hasSize(databaseSizeBeforeUpdate);
        Palier testPalier = palierList.get(palierList.size() - 1);
        assertThat(testPalier.getDatedebut()).isEqualTo(UPDATED_DATEDEBUT);
        assertThat(testPalier.getDatefin()).isEqualTo(UPDATED_DATEFIN);
        assertThat(testPalier.getNombrecons()).isEqualTo(UPDATED_NOMBRECONS);
        assertThat(testPalier.getPromotion()).isEqualTo(UPDATED_PROMOTION);

        // Validate the Palier in Elasticsearch
        Palier palierEs = palierSearchRepository.findOne(testPalier.getId());
        assertThat(palierEs).isEqualToComparingFieldByField(testPalier);
    }

    @Test
    @Transactional
    public void updateNonExistingPalier() throws Exception {
        int databaseSizeBeforeUpdate = palierRepository.findAll().size();

        // Create the Palier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPalierMockMvc.perform(put("/api/paliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(palier)))
            .andExpect(status().isCreated());

        // Validate the Palier in the database
        List<Palier> palierList = palierRepository.findAll();
        assertThat(palierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePalier() throws Exception {
        // Initialize the database
        palierRepository.saveAndFlush(palier);
        palierSearchRepository.save(palier);
        int databaseSizeBeforeDelete = palierRepository.findAll().size();

        // Get the palier
        restPalierMockMvc.perform(delete("/api/paliers/{id}", palier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean palierExistsInEs = palierSearchRepository.exists(palier.getId());
        assertThat(palierExistsInEs).isFalse();

        // Validate the database is empty
        List<Palier> palierList = palierRepository.findAll();
        assertThat(palierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPalier() throws Exception {
        // Initialize the database
        palierRepository.saveAndFlush(palier);
        palierSearchRepository.save(palier);

        // Search the palier
        restPalierMockMvc.perform(get("/api/_search/paliers?query=id:" + palier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palier.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT.toString())))
            .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN.toString())))
            .andExpect(jsonPath("$.[*].nombrecons").value(hasItem(DEFAULT_NOMBRECONS)))
            .andExpect(jsonPath("$.[*].promotion").value(hasItem(DEFAULT_PROMOTION.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Palier.class);
    }
}

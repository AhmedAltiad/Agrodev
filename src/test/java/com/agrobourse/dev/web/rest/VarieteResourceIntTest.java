package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Variete;
import com.agrobourse.dev.repository.VarieteRepository;
import com.agrobourse.dev.repository.search.VarieteSearchRepository;
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
 * Test class for the VarieteResource REST controller.
 *
 * @see VarieteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class VarieteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private VarieteRepository varieteRepository;

    @Autowired
    private VarieteSearchRepository varieteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVarieteMockMvc;

    private Variete variete;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VarieteResource varieteResource = new VarieteResource(varieteRepository, varieteSearchRepository);
        this.restVarieteMockMvc = MockMvcBuilders.standaloneSetup(varieteResource)
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
    public static Variete createEntity(EntityManager em) {
        Variete variete = new Variete()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE);
        return variete;
    }

    @Before
    public void initTest() {
        varieteSearchRepository.deleteAll();
        variete = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariete() throws Exception {
        int databaseSizeBeforeCreate = varieteRepository.findAll().size();

        // Create the Variete
        restVarieteMockMvc.perform(post("/api/varietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variete)))
            .andExpect(status().isCreated());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeCreate + 1);
        Variete testVariete = varieteList.get(varieteList.size() - 1);
        assertThat(testVariete.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVariete.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVariete.getImage()).isEqualTo(DEFAULT_IMAGE);

        // Validate the Variete in Elasticsearch
        Variete varieteEs = varieteSearchRepository.findOne(testVariete.getId());
        assertThat(varieteEs).isEqualToComparingFieldByField(testVariete);
    }

    @Test
    @Transactional
    public void createVarieteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = varieteRepository.findAll().size();

        // Create the Variete with an existing ID
        variete.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarieteMockMvc.perform(post("/api/varietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variete)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVarietes() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        // Get all the varieteList
        restVarieteMockMvc.perform(get("/api/varietes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variete.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);

        // Get the variete
        restVarieteMockMvc.perform(get("/api/varietes/{id}", variete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variete.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVariete() throws Exception {
        // Get the variete
        restVarieteMockMvc.perform(get("/api/varietes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);
        varieteSearchRepository.save(variete);
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();

        // Update the variete
        Variete updatedVariete = varieteRepository.findOne(variete.getId());
        updatedVariete
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE);

        restVarieteMockMvc.perform(put("/api/varietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVariete)))
            .andExpect(status().isOk());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate);
        Variete testVariete = varieteList.get(varieteList.size() - 1);
        assertThat(testVariete.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVariete.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVariete.getImage()).isEqualTo(UPDATED_IMAGE);

        // Validate the Variete in Elasticsearch
        Variete varieteEs = varieteSearchRepository.findOne(testVariete.getId());
        assertThat(varieteEs).isEqualToComparingFieldByField(testVariete);
    }

    @Test
    @Transactional
    public void updateNonExistingVariete() throws Exception {
        int databaseSizeBeforeUpdate = varieteRepository.findAll().size();

        // Create the Variete

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVarieteMockMvc.perform(put("/api/varietes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variete)))
            .andExpect(status().isCreated());

        // Validate the Variete in the database
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);
        varieteSearchRepository.save(variete);
        int databaseSizeBeforeDelete = varieteRepository.findAll().size();

        // Get the variete
        restVarieteMockMvc.perform(delete("/api/varietes/{id}", variete.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean varieteExistsInEs = varieteSearchRepository.exists(variete.getId());
        assertThat(varieteExistsInEs).isFalse();

        // Validate the database is empty
        List<Variete> varieteList = varieteRepository.findAll();
        assertThat(varieteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVariete() throws Exception {
        // Initialize the database
        varieteRepository.saveAndFlush(variete);
        varieteSearchRepository.save(variete);

        // Search the variete
        restVarieteMockMvc.perform(get("/api/_search/varietes?query=id:" + variete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variete.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variete.class);
    }
}

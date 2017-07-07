package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Localite;
import com.agrobourse.dev.repository.LocaliteRepository;
import com.agrobourse.dev.repository.search.LocaliteSearchRepository;
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
 * Test class for the LocaliteResource REST controller.
 *
 * @see LocaliteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class LocaliteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODEPOSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODEPOSTAL = "BBBBBBBBBB";

    @Autowired
    private LocaliteRepository localiteRepository;

    @Autowired
    private LocaliteSearchRepository localiteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocaliteMockMvc;

    private Localite localite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocaliteResource localiteResource = new LocaliteResource(localiteRepository, localiteSearchRepository);
        this.restLocaliteMockMvc = MockMvcBuilders.standaloneSetup(localiteResource)
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
    public static Localite createEntity(EntityManager em) {
        Localite localite = new Localite()
            .name(DEFAULT_NAME)
            .codepostal(DEFAULT_CODEPOSTAL);
        return localite;
    }

    @Before
    public void initTest() {
        localiteSearchRepository.deleteAll();
        localite = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalite() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();

        // Create the Localite
        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isCreated());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate + 1);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocalite.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);

        // Validate the Localite in Elasticsearch
        Localite localiteEs = localiteSearchRepository.findOne(testLocalite.getId());
        assertThat(localiteEs).isEqualToComparingFieldByField(testLocalite);
    }

    @Test
    @Transactional
    public void createLocaliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();

        // Create the Localite with an existing ID
        localite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalites() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        // Get all the localiteList
        restLocaliteMockMvc.perform(get("/api/localites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())));
    }

    @Test
    @Transactional
    public void getLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        // Get the localite
        restLocaliteMockMvc.perform(get("/api/localites/{id}", localite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.codepostal").value(DEFAULT_CODEPOSTAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalite() throws Exception {
        // Get the localite
        restLocaliteMockMvc.perform(get("/api/localites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);
        localiteSearchRepository.save(localite);
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Update the localite
        Localite updatedLocalite = localiteRepository.findOne(localite.getId());
        updatedLocalite
            .name(UPDATED_NAME)
            .codepostal(UPDATED_CODEPOSTAL);

        restLocaliteMockMvc.perform(put("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalite)))
            .andExpect(status().isOk());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocalite.getCodepostal()).isEqualTo(UPDATED_CODEPOSTAL);

        // Validate the Localite in Elasticsearch
        Localite localiteEs = localiteSearchRepository.findOne(testLocalite.getId());
        assertThat(localiteEs).isEqualToComparingFieldByField(testLocalite);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Create the Localite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocaliteMockMvc.perform(put("/api/localites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isCreated());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);
        localiteSearchRepository.save(localite);
        int databaseSizeBeforeDelete = localiteRepository.findAll().size();

        // Get the localite
        restLocaliteMockMvc.perform(delete("/api/localites/{id}", localite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean localiteExistsInEs = localiteSearchRepository.exists(localite.getId());
        assertThat(localiteExistsInEs).isFalse();

        // Validate the database is empty
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);
        localiteSearchRepository.save(localite);

        // Search the localite
        restLocaliteMockMvc.perform(get("/api/_search/localites?query=id:" + localite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localite.class);
    }
}

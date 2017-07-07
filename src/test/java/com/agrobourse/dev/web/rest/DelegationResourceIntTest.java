package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Delegation;
import com.agrobourse.dev.repository.DelegationRepository;
import com.agrobourse.dev.repository.search.DelegationSearchRepository;
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
 * Test class for the DelegationResource REST controller.
 *
 * @see DelegationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class DelegationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DelegationRepository delegationRepository;

    @Autowired
    private DelegationSearchRepository delegationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDelegationMockMvc;

    private Delegation delegation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DelegationResource delegationResource = new DelegationResource(delegationRepository, delegationSearchRepository);
        this.restDelegationMockMvc = MockMvcBuilders.standaloneSetup(delegationResource)
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
    public static Delegation createEntity(EntityManager em) {
        Delegation delegation = new Delegation()
            .name(DEFAULT_NAME);
        return delegation;
    }

    @Before
    public void initTest() {
        delegationSearchRepository.deleteAll();
        delegation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelegation() throws Exception {
        int databaseSizeBeforeCreate = delegationRepository.findAll().size();

        // Create the Delegation
        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegation)))
            .andExpect(status().isCreated());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeCreate + 1);
        Delegation testDelegation = delegationList.get(delegationList.size() - 1);
        assertThat(testDelegation.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Delegation in Elasticsearch
        Delegation delegationEs = delegationSearchRepository.findOne(testDelegation.getId());
        assertThat(delegationEs).isEqualToComparingFieldByField(testDelegation);
    }

    @Test
    @Transactional
    public void createDelegationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = delegationRepository.findAll().size();

        // Create the Delegation with an existing ID
        delegation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDelegations() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);

        // Get all the delegationList
        restDelegationMockMvc.perform(get("/api/delegations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);

        // Get the delegation
        restDelegationMockMvc.perform(get("/api/delegations/{id}", delegation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delegation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDelegation() throws Exception {
        // Get the delegation
        restDelegationMockMvc.perform(get("/api/delegations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);
        delegationSearchRepository.save(delegation);
        int databaseSizeBeforeUpdate = delegationRepository.findAll().size();

        // Update the delegation
        Delegation updatedDelegation = delegationRepository.findOne(delegation.getId());
        updatedDelegation
            .name(UPDATED_NAME);

        restDelegationMockMvc.perform(put("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDelegation)))
            .andExpect(status().isOk());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeUpdate);
        Delegation testDelegation = delegationList.get(delegationList.size() - 1);
        assertThat(testDelegation.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Delegation in Elasticsearch
        Delegation delegationEs = delegationSearchRepository.findOne(testDelegation.getId());
        assertThat(delegationEs).isEqualToComparingFieldByField(testDelegation);
    }

    @Test
    @Transactional
    public void updateNonExistingDelegation() throws Exception {
        int databaseSizeBeforeUpdate = delegationRepository.findAll().size();

        // Create the Delegation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDelegationMockMvc.perform(put("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegation)))
            .andExpect(status().isCreated());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);
        delegationSearchRepository.save(delegation);
        int databaseSizeBeforeDelete = delegationRepository.findAll().size();

        // Get the delegation
        restDelegationMockMvc.perform(delete("/api/delegations/{id}", delegation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean delegationExistsInEs = delegationSearchRepository.exists(delegation.getId());
        assertThat(delegationExistsInEs).isFalse();

        // Validate the database is empty
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);
        delegationSearchRepository.save(delegation);

        // Search the delegation
        restDelegationMockMvc.perform(get("/api/_search/delegations?query=id:" + delegation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delegation.class);
    }
}

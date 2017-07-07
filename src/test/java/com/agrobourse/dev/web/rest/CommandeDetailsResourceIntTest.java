package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.CommandeDetails;
import com.agrobourse.dev.repository.CommandeDetailsRepository;
import com.agrobourse.dev.repository.search.CommandeDetailsSearchRepository;
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
 * Test class for the CommandeDetailsResource REST controller.
 *
 * @see CommandeDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class CommandeDetailsResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private CommandeDetailsRepository commandeDetailsRepository;

    @Autowired
    private CommandeDetailsSearchRepository commandeDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommandeDetailsMockMvc;

    private CommandeDetails commandeDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommandeDetailsResource commandeDetailsResource = new CommandeDetailsResource(commandeDetailsRepository, commandeDetailsSearchRepository);
        this.restCommandeDetailsMockMvc = MockMvcBuilders.standaloneSetup(commandeDetailsResource)
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
    public static CommandeDetails createEntity(EntityManager em) {
        CommandeDetails commandeDetails = new CommandeDetails()
            .price(DEFAULT_PRICE);
        return commandeDetails;
    }

    @Before
    public void initTest() {
        commandeDetailsSearchRepository.deleteAll();
        commandeDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeDetails() throws Exception {
        int databaseSizeBeforeCreate = commandeDetailsRepository.findAll().size();

        // Create the CommandeDetails
        restCommandeDetailsMockMvc.perform(post("/api/commande-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeDetails)))
            .andExpect(status().isCreated());

        // Validate the CommandeDetails in the database
        List<CommandeDetails> commandeDetailsList = commandeDetailsRepository.findAll();
        assertThat(commandeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeDetails testCommandeDetails = commandeDetailsList.get(commandeDetailsList.size() - 1);
        assertThat(testCommandeDetails.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the CommandeDetails in Elasticsearch
        CommandeDetails commandeDetailsEs = commandeDetailsSearchRepository.findOne(testCommandeDetails.getId());
        assertThat(commandeDetailsEs).isEqualToComparingFieldByField(testCommandeDetails);
    }

    @Test
    @Transactional
    public void createCommandeDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeDetailsRepository.findAll().size();

        // Create the CommandeDetails with an existing ID
        commandeDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeDetailsMockMvc.perform(post("/api/commande-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CommandeDetails> commandeDetailsList = commandeDetailsRepository.findAll();
        assertThat(commandeDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommandeDetails() throws Exception {
        // Initialize the database
        commandeDetailsRepository.saveAndFlush(commandeDetails);

        // Get all the commandeDetailsList
        restCommandeDetailsMockMvc.perform(get("/api/commande-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCommandeDetails() throws Exception {
        // Initialize the database
        commandeDetailsRepository.saveAndFlush(commandeDetails);

        // Get the commandeDetails
        restCommandeDetailsMockMvc.perform(get("/api/commande-details/{id}", commandeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commandeDetails.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeDetails() throws Exception {
        // Get the commandeDetails
        restCommandeDetailsMockMvc.perform(get("/api/commande-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeDetails() throws Exception {
        // Initialize the database
        commandeDetailsRepository.saveAndFlush(commandeDetails);
        commandeDetailsSearchRepository.save(commandeDetails);
        int databaseSizeBeforeUpdate = commandeDetailsRepository.findAll().size();

        // Update the commandeDetails
        CommandeDetails updatedCommandeDetails = commandeDetailsRepository.findOne(commandeDetails.getId());
        updatedCommandeDetails
            .price(UPDATED_PRICE);

        restCommandeDetailsMockMvc.perform(put("/api/commande-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandeDetails)))
            .andExpect(status().isOk());

        // Validate the CommandeDetails in the database
        List<CommandeDetails> commandeDetailsList = commandeDetailsRepository.findAll();
        assertThat(commandeDetailsList).hasSize(databaseSizeBeforeUpdate);
        CommandeDetails testCommandeDetails = commandeDetailsList.get(commandeDetailsList.size() - 1);
        assertThat(testCommandeDetails.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the CommandeDetails in Elasticsearch
        CommandeDetails commandeDetailsEs = commandeDetailsSearchRepository.findOne(testCommandeDetails.getId());
        assertThat(commandeDetailsEs).isEqualToComparingFieldByField(testCommandeDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeDetails() throws Exception {
        int databaseSizeBeforeUpdate = commandeDetailsRepository.findAll().size();

        // Create the CommandeDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommandeDetailsMockMvc.perform(put("/api/commande-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandeDetails)))
            .andExpect(status().isCreated());

        // Validate the CommandeDetails in the database
        List<CommandeDetails> commandeDetailsList = commandeDetailsRepository.findAll();
        assertThat(commandeDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommandeDetails() throws Exception {
        // Initialize the database
        commandeDetailsRepository.saveAndFlush(commandeDetails);
        commandeDetailsSearchRepository.save(commandeDetails);
        int databaseSizeBeforeDelete = commandeDetailsRepository.findAll().size();

        // Get the commandeDetails
        restCommandeDetailsMockMvc.perform(delete("/api/commande-details/{id}", commandeDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean commandeDetailsExistsInEs = commandeDetailsSearchRepository.exists(commandeDetails.getId());
        assertThat(commandeDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<CommandeDetails> commandeDetailsList = commandeDetailsRepository.findAll();
        assertThat(commandeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCommandeDetails() throws Exception {
        // Initialize the database
        commandeDetailsRepository.saveAndFlush(commandeDetails);
        commandeDetailsSearchRepository.save(commandeDetails);

        // Search the commandeDetails
        restCommandeDetailsMockMvc.perform(get("/api/_search/commande-details?query=id:" + commandeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeDetails.class);
    }
}

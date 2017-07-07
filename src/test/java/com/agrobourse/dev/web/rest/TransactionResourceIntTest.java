package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.Transaction;
import com.agrobourse.dev.repository.TransactionRepository;
import com.agrobourse.dev.repository.search.TransactionSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.agrobourse.dev.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TransactionResource REST controller.
 *
 * @see TransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class TransactionResourceIntTest {

    private static final Integer DEFAULT_ETAT = 1;
    private static final Integer UPDATED_ETAT = 2;

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_NUMTRANSACTION = 1L;
    private static final Long UPDATED_NUMTRANSACTION = 2L;

    private static final Integer DEFAULT_CMDSPAYEES = 1;
    private static final Integer UPDATED_CMDSPAYEES = 2;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION_PAIMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION_PAIMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_REFUSE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REFUSE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_REFUSPAIMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_REFUSPAIMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionSearchRepository transactionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionResource transactionResource = new TransactionResource(transactionRepository, transactionSearchRepository);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
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
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .etat(DEFAULT_ETAT)
            .prix(DEFAULT_PRIX)
            .date(DEFAULT_DATE)
            .numtransaction(DEFAULT_NUMTRANSACTION)
            .cmdspayees(DEFAULT_CMDSPAYEES)
            .image(DEFAULT_IMAGE)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .dateValidationPaiment(DEFAULT_DATE_VALIDATION_PAIMENT)
            .dateRefuse(DEFAULT_DATE_REFUSE)
            .dateRefuspaiment(DEFAULT_DATE_REFUSPAIMENT);
        return transaction;
    }

    @Before
    public void initTest() {
        transactionSearchRepository.deleteAll();
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testTransaction.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTransaction.getNumtransaction()).isEqualTo(DEFAULT_NUMTRANSACTION);
        assertThat(testTransaction.getCmdspayees()).isEqualTo(DEFAULT_CMDSPAYEES);
        assertThat(testTransaction.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testTransaction.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testTransaction.getDateValidationPaiment()).isEqualTo(DEFAULT_DATE_VALIDATION_PAIMENT);
        assertThat(testTransaction.getDateRefuse()).isEqualTo(DEFAULT_DATE_REFUSE);
        assertThat(testTransaction.getDateRefuspaiment()).isEqualTo(DEFAULT_DATE_REFUSPAIMENT);

        // Validate the Transaction in Elasticsearch
        Transaction transactionEs = transactionSearchRepository.findOne(testTransaction.getId());
        assertThat(transactionEs).isEqualToComparingFieldByField(testTransaction);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numtransaction").value(hasItem(DEFAULT_NUMTRANSACTION.intValue())))
            .andExpect(jsonPath("$.[*].cmdspayees").value(hasItem(DEFAULT_CMDSPAYEES)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].dateValidationPaiment").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION_PAIMENT))))
            .andExpect(jsonPath("$.[*].dateRefuse").value(hasItem(sameInstant(DEFAULT_DATE_REFUSE))))
            .andExpect(jsonPath("$.[*].dateRefuspaiment").value(hasItem(sameInstant(DEFAULT_DATE_REFUSPAIMENT))));
    }

    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.numtransaction").value(DEFAULT_NUMTRANSACTION.intValue()))
            .andExpect(jsonPath("$.cmdspayees").value(DEFAULT_CMDSPAYEES))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.dateValidation").value(sameInstant(DEFAULT_DATE_VALIDATION)))
            .andExpect(jsonPath("$.dateValidationPaiment").value(sameInstant(DEFAULT_DATE_VALIDATION_PAIMENT)))
            .andExpect(jsonPath("$.dateRefuse").value(sameInstant(DEFAULT_DATE_REFUSE)))
            .andExpect(jsonPath("$.dateRefuspaiment").value(sameInstant(DEFAULT_DATE_REFUSPAIMENT)));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        transactionSearchRepository.save(transaction);
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findOne(transaction.getId());
        updatedTransaction
            .etat(UPDATED_ETAT)
            .prix(UPDATED_PRIX)
            .date(UPDATED_DATE)
            .numtransaction(UPDATED_NUMTRANSACTION)
            .cmdspayees(UPDATED_CMDSPAYEES)
            .image(UPDATED_IMAGE)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .dateValidationPaiment(UPDATED_DATE_VALIDATION_PAIMENT)
            .dateRefuse(UPDATED_DATE_REFUSE)
            .dateRefuspaiment(UPDATED_DATE_REFUSPAIMENT);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testTransaction.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testTransaction.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTransaction.getNumtransaction()).isEqualTo(UPDATED_NUMTRANSACTION);
        assertThat(testTransaction.getCmdspayees()).isEqualTo(UPDATED_CMDSPAYEES);
        assertThat(testTransaction.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testTransaction.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testTransaction.getDateValidationPaiment()).isEqualTo(UPDATED_DATE_VALIDATION_PAIMENT);
        assertThat(testTransaction.getDateRefuse()).isEqualTo(UPDATED_DATE_REFUSE);
        assertThat(testTransaction.getDateRefuspaiment()).isEqualTo(UPDATED_DATE_REFUSPAIMENT);

        // Validate the Transaction in Elasticsearch
        Transaction transactionEs = transactionSearchRepository.findOne(testTransaction.getId());
        assertThat(transactionEs).isEqualToComparingFieldByField(testTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        transactionSearchRepository.save(transaction);
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transactionExistsInEs = transactionSearchRepository.exists(transaction.getId());
        assertThat(transactionExistsInEs).isFalse();

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        transactionSearchRepository.save(transaction);

        // Search the transaction
        restTransactionMockMvc.perform(get("/api/_search/transactions?query=id:" + transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].numtransaction").value(hasItem(DEFAULT_NUMTRANSACTION.intValue())))
            .andExpect(jsonPath("$.[*].cmdspayees").value(hasItem(DEFAULT_CMDSPAYEES)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].dateValidationPaiment").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION_PAIMENT))))
            .andExpect(jsonPath("$.[*].dateRefuse").value(hasItem(sameInstant(DEFAULT_DATE_REFUSE))))
            .andExpect(jsonPath("$.[*].dateRefuspaiment").value(hasItem(sameInstant(DEFAULT_DATE_REFUSPAIMENT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
    }
}

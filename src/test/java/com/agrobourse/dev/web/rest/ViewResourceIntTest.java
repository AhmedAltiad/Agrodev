package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.View;
import com.agrobourse.dev.repository.ViewRepository;
import com.agrobourse.dev.repository.search.ViewSearchRepository;
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
 * Test class for the ViewResource REST controller.
 *
 * @see ViewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class ViewResourceIntTest {

    private static final String DEFAULT_CONSOLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONSOLE_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DURATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DURATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private ViewSearchRepository viewSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restViewMockMvc;

    private View view;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ViewResource viewResource = new ViewResource(viewRepository, viewSearchRepository);
        this.restViewMockMvc = MockMvcBuilders.standaloneSetup(viewResource)
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
    public static View createEntity(EntityManager em) {
        View view = new View()
            .consoleType(DEFAULT_CONSOLE_TYPE)
            .duration(DEFAULT_DURATION);
        return view;
    }

    @Before
    public void initTest() {
        viewSearchRepository.deleteAll();
        view = createEntity(em);
    }

    @Test
    @Transactional
    public void createView() throws Exception {
        int databaseSizeBeforeCreate = viewRepository.findAll().size();

        // Create the View
        restViewMockMvc.perform(post("/api/views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(view)))
            .andExpect(status().isCreated());

        // Validate the View in the database
        List<View> viewList = viewRepository.findAll();
        assertThat(viewList).hasSize(databaseSizeBeforeCreate + 1);
        View testView = viewList.get(viewList.size() - 1);
        assertThat(testView.getConsoleType()).isEqualTo(DEFAULT_CONSOLE_TYPE);
        assertThat(testView.getDuration()).isEqualTo(DEFAULT_DURATION);

        // Validate the View in Elasticsearch
        View viewEs = viewSearchRepository.findOne(testView.getId());
        assertThat(viewEs).isEqualToComparingFieldByField(testView);
    }

    @Test
    @Transactional
    public void createViewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = viewRepository.findAll().size();

        // Create the View with an existing ID
        view.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewMockMvc.perform(post("/api/views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(view)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<View> viewList = viewRepository.findAll();
        assertThat(viewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllViews() throws Exception {
        // Initialize the database
        viewRepository.saveAndFlush(view);

        // Get all the viewList
        restViewMockMvc.perform(get("/api/views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(view.getId().intValue())))
            .andExpect(jsonPath("$.[*].consoleType").value(hasItem(DEFAULT_CONSOLE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())));
    }

    @Test
    @Transactional
    public void getView() throws Exception {
        // Initialize the database
        viewRepository.saveAndFlush(view);

        // Get the view
        restViewMockMvc.perform(get("/api/views/{id}", view.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(view.getId().intValue()))
            .andExpect(jsonPath("$.consoleType").value(DEFAULT_CONSOLE_TYPE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingView() throws Exception {
        // Get the view
        restViewMockMvc.perform(get("/api/views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateView() throws Exception {
        // Initialize the database
        viewRepository.saveAndFlush(view);
        viewSearchRepository.save(view);
        int databaseSizeBeforeUpdate = viewRepository.findAll().size();

        // Update the view
        View updatedView = viewRepository.findOne(view.getId());
        updatedView
            .consoleType(UPDATED_CONSOLE_TYPE)
            .duration(UPDATED_DURATION);

        restViewMockMvc.perform(put("/api/views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedView)))
            .andExpect(status().isOk());

        // Validate the View in the database
        List<View> viewList = viewRepository.findAll();
        assertThat(viewList).hasSize(databaseSizeBeforeUpdate);
        View testView = viewList.get(viewList.size() - 1);
        assertThat(testView.getConsoleType()).isEqualTo(UPDATED_CONSOLE_TYPE);
        assertThat(testView.getDuration()).isEqualTo(UPDATED_DURATION);

        // Validate the View in Elasticsearch
        View viewEs = viewSearchRepository.findOne(testView.getId());
        assertThat(viewEs).isEqualToComparingFieldByField(testView);
    }

    @Test
    @Transactional
    public void updateNonExistingView() throws Exception {
        int databaseSizeBeforeUpdate = viewRepository.findAll().size();

        // Create the View

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restViewMockMvc.perform(put("/api/views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(view)))
            .andExpect(status().isCreated());

        // Validate the View in the database
        List<View> viewList = viewRepository.findAll();
        assertThat(viewList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteView() throws Exception {
        // Initialize the database
        viewRepository.saveAndFlush(view);
        viewSearchRepository.save(view);
        int databaseSizeBeforeDelete = viewRepository.findAll().size();

        // Get the view
        restViewMockMvc.perform(delete("/api/views/{id}", view.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean viewExistsInEs = viewSearchRepository.exists(view.getId());
        assertThat(viewExistsInEs).isFalse();

        // Validate the database is empty
        List<View> viewList = viewRepository.findAll();
        assertThat(viewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchView() throws Exception {
        // Initialize the database
        viewRepository.saveAndFlush(view);
        viewSearchRepository.save(view);

        // Search the view
        restViewMockMvc.perform(get("/api/_search/views?query=id:" + view.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(view.getId().intValue())))
            .andExpect(jsonPath("$.[*].consoleType").value(hasItem(DEFAULT_CONSOLE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(View.class);
    }
}

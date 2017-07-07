package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.View;

import com.agrobourse.dev.repository.ViewRepository;
import com.agrobourse.dev.repository.search.ViewSearchRepository;
import com.agrobourse.dev.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing View.
 */
@RestController
@RequestMapping("/api")
public class ViewResource {

    private final Logger log = LoggerFactory.getLogger(ViewResource.class);

    private static final String ENTITY_NAME = "view";
        
    private final ViewRepository viewRepository;

    private final ViewSearchRepository viewSearchRepository;

    public ViewResource(ViewRepository viewRepository, ViewSearchRepository viewSearchRepository) {
        this.viewRepository = viewRepository;
        this.viewSearchRepository = viewSearchRepository;
    }

    /**
     * POST  /views : Create a new view.
     *
     * @param view the view to create
     * @return the ResponseEntity with status 201 (Created) and with body the new view, or with status 400 (Bad Request) if the view has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/views")
    @Timed
    public ResponseEntity<View> createView(@RequestBody View view) throws URISyntaxException {
        log.debug("REST request to save View : {}", view);
        if (view.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new view cannot already have an ID")).body(null);
        }
        View result = viewRepository.save(view);
        viewSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /views : Updates an existing view.
     *
     * @param view the view to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated view,
     * or with status 400 (Bad Request) if the view is not valid,
     * or with status 500 (Internal Server Error) if the view couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/views")
    @Timed
    public ResponseEntity<View> updateView(@RequestBody View view) throws URISyntaxException {
        log.debug("REST request to update View : {}", view);
        if (view.getId() == null) {
            return createView(view);
        }
        View result = viewRepository.save(view);
        viewSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, view.getId().toString()))
            .body(result);
    }

    /**
     * GET  /views : get all the views.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of views in body
     */
    @GetMapping("/views")
    @Timed
    public List<View> getAllViews() {
        log.debug("REST request to get all Views");
        List<View> views = viewRepository.findAll();
        return views;
    }

    /**
     * GET  /views/:id : get the "id" view.
     *
     * @param id the id of the view to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the view, or with status 404 (Not Found)
     */
    @GetMapping("/views/{id}")
    @Timed
    public ResponseEntity<View> getView(@PathVariable Long id) {
        log.debug("REST request to get View : {}", id);
        View view = viewRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(view));
    }

    /**
     * DELETE  /views/:id : delete the "id" view.
     *
     * @param id the id of the view to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/views/{id}")
    @Timed
    public ResponseEntity<Void> deleteView(@PathVariable Long id) {
        log.debug("REST request to delete View : {}", id);
        viewRepository.delete(id);
        viewSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/views?query=:query : search for the view corresponding
     * to the query.
     *
     * @param query the query of the view search 
     * @return the result of the search
     */
    @GetMapping("/_search/views")
    @Timed
    public List<View> searchViews(@RequestParam String query) {
        log.debug("REST request to search Views for query {}", query);
        return StreamSupport
            .stream(viewSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

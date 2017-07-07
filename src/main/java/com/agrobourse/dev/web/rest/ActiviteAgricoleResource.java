package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.ActiviteAgricole;

import com.agrobourse.dev.repository.ActiviteAgricoleRepository;
import com.agrobourse.dev.repository.search.ActiviteAgricoleSearchRepository;
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
 * REST controller for managing ActiviteAgricole.
 */
@RestController
@RequestMapping("/api")
public class ActiviteAgricoleResource {

    private final Logger log = LoggerFactory.getLogger(ActiviteAgricoleResource.class);

    private static final String ENTITY_NAME = "activiteAgricole";
        
    private final ActiviteAgricoleRepository activiteAgricoleRepository;

    private final ActiviteAgricoleSearchRepository activiteAgricoleSearchRepository;

    public ActiviteAgricoleResource(ActiviteAgricoleRepository activiteAgricoleRepository, ActiviteAgricoleSearchRepository activiteAgricoleSearchRepository) {
        this.activiteAgricoleRepository = activiteAgricoleRepository;
        this.activiteAgricoleSearchRepository = activiteAgricoleSearchRepository;
    }

    /**
     * POST  /activite-agricoles : Create a new activiteAgricole.
     *
     * @param activiteAgricole the activiteAgricole to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activiteAgricole, or with status 400 (Bad Request) if the activiteAgricole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activite-agricoles")
    @Timed
    public ResponseEntity<ActiviteAgricole> createActiviteAgricole(@RequestBody ActiviteAgricole activiteAgricole) throws URISyntaxException {
        log.debug("REST request to save ActiviteAgricole : {}", activiteAgricole);
        if (activiteAgricole.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new activiteAgricole cannot already have an ID")).body(null);
        }
        ActiviteAgricole result = activiteAgricoleRepository.save(activiteAgricole);
        activiteAgricoleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/activite-agricoles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activite-agricoles : Updates an existing activiteAgricole.
     *
     * @param activiteAgricole the activiteAgricole to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activiteAgricole,
     * or with status 400 (Bad Request) if the activiteAgricole is not valid,
     * or with status 500 (Internal Server Error) if the activiteAgricole couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activite-agricoles")
    @Timed
    public ResponseEntity<ActiviteAgricole> updateActiviteAgricole(@RequestBody ActiviteAgricole activiteAgricole) throws URISyntaxException {
        log.debug("REST request to update ActiviteAgricole : {}", activiteAgricole);
        if (activiteAgricole.getId() == null) {
            return createActiviteAgricole(activiteAgricole);
        }
        ActiviteAgricole result = activiteAgricoleRepository.save(activiteAgricole);
        activiteAgricoleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activiteAgricole.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activite-agricoles : get all the activiteAgricoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activiteAgricoles in body
     */
    @GetMapping("/activite-agricoles")
    @Timed
    public List<ActiviteAgricole> getAllActiviteAgricoles() {
        log.debug("REST request to get all ActiviteAgricoles");
        List<ActiviteAgricole> activiteAgricoles = activiteAgricoleRepository.findAll();
        return activiteAgricoles;
    }

    /**
     * GET  /activite-agricoles/:id : get the "id" activiteAgricole.
     *
     * @param id the id of the activiteAgricole to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activiteAgricole, or with status 404 (Not Found)
     */
    @GetMapping("/activite-agricoles/{id}")
    @Timed
    public ResponseEntity<ActiviteAgricole> getActiviteAgricole(@PathVariable Long id) {
        log.debug("REST request to get ActiviteAgricole : {}", id);
        ActiviteAgricole activiteAgricole = activiteAgricoleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activiteAgricole));
    }

    /**
     * DELETE  /activite-agricoles/:id : delete the "id" activiteAgricole.
     *
     * @param id the id of the activiteAgricole to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activite-agricoles/{id}")
    @Timed
    public ResponseEntity<Void> deleteActiviteAgricole(@PathVariable Long id) {
        log.debug("REST request to delete ActiviteAgricole : {}", id);
        activiteAgricoleRepository.delete(id);
        activiteAgricoleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/activite-agricoles?query=:query : search for the activiteAgricole corresponding
     * to the query.
     *
     * @param query the query of the activiteAgricole search 
     * @return the result of the search
     */
    @GetMapping("/_search/activite-agricoles")
    @Timed
    public List<ActiviteAgricole> searchActiviteAgricoles(@RequestParam String query) {
        log.debug("REST request to search ActiviteAgricoles for query {}", query);
        return StreamSupport
            .stream(activiteAgricoleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

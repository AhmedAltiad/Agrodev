package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Variete;

import com.agrobourse.dev.repository.VarieteRepository;
import com.agrobourse.dev.repository.search.VarieteSearchRepository;
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
 * REST controller for managing Variete.
 */
@RestController
@RequestMapping("/api")
public class VarieteResource {

    private final Logger log = LoggerFactory.getLogger(VarieteResource.class);

    private static final String ENTITY_NAME = "variete";
        
    private final VarieteRepository varieteRepository;

    private final VarieteSearchRepository varieteSearchRepository;

    public VarieteResource(VarieteRepository varieteRepository, VarieteSearchRepository varieteSearchRepository) {
        this.varieteRepository = varieteRepository;
        this.varieteSearchRepository = varieteSearchRepository;
    }

    /**
     * POST  /varietes : Create a new variete.
     *
     * @param variete the variete to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variete, or with status 400 (Bad Request) if the variete has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/varietes")
    @Timed
    public ResponseEntity<Variete> createVariete(@RequestBody Variete variete) throws URISyntaxException {
        log.debug("REST request to save Variete : {}", variete);
        if (variete.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new variete cannot already have an ID")).body(null);
        }
        Variete result = varieteRepository.save(variete);
        varieteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/varietes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /varietes : Updates an existing variete.
     *
     * @param variete the variete to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variete,
     * or with status 400 (Bad Request) if the variete is not valid,
     * or with status 500 (Internal Server Error) if the variete couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/varietes")
    @Timed
    public ResponseEntity<Variete> updateVariete(@RequestBody Variete variete) throws URISyntaxException {
        log.debug("REST request to update Variete : {}", variete);
        if (variete.getId() == null) {
            return createVariete(variete);
        }
        Variete result = varieteRepository.save(variete);
        varieteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variete.getId().toString()))
            .body(result);
    }

    /**
     * GET  /varietes : get all the varietes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of varietes in body
     */
    @GetMapping("/varietes")
    @Timed
    public List<Variete> getAllVarietes() {
        log.debug("REST request to get all Varietes");
        List<Variete> varietes = varieteRepository.findAll();
        return varietes;
    }

    /**
     * GET  /varietes/:id : get the "id" variete.
     *
     * @param id the id of the variete to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variete, or with status 404 (Not Found)
     */
    @GetMapping("/varietes/{id}")
    @Timed
    public ResponseEntity<Variete> getVariete(@PathVariable Long id) {
        log.debug("REST request to get Variete : {}", id);
        Variete variete = varieteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(variete));
    }

    /**
     * DELETE  /varietes/:id : delete the "id" variete.
     *
     * @param id the id of the variete to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/varietes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVariete(@PathVariable Long id) {
        log.debug("REST request to delete Variete : {}", id);
        varieteRepository.delete(id);
        varieteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/varietes?query=:query : search for the variete corresponding
     * to the query.
     *
     * @param query the query of the variete search 
     * @return the result of the search
     */
    @GetMapping("/_search/varietes")
    @Timed
    public List<Variete> searchVarietes(@RequestParam String query) {
        log.debug("REST request to search Varietes for query {}", query);
        return StreamSupport
            .stream(varieteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

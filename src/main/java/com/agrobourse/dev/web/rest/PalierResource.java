package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Palier;

import com.agrobourse.dev.repository.PalierRepository;
import com.agrobourse.dev.repository.search.PalierSearchRepository;
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
 * REST controller for managing Palier.
 */
@RestController
@RequestMapping("/api")
public class PalierResource {

    private final Logger log = LoggerFactory.getLogger(PalierResource.class);

    private static final String ENTITY_NAME = "palier";
        
    private final PalierRepository palierRepository;

    private final PalierSearchRepository palierSearchRepository;

    public PalierResource(PalierRepository palierRepository, PalierSearchRepository palierSearchRepository) {
        this.palierRepository = palierRepository;
        this.palierSearchRepository = palierSearchRepository;
    }

    /**
     * POST  /paliers : Create a new palier.
     *
     * @param palier the palier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new palier, or with status 400 (Bad Request) if the palier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paliers")
    @Timed
    public ResponseEntity<Palier> createPalier(@RequestBody Palier palier) throws URISyntaxException {
        log.debug("REST request to save Palier : {}", palier);
        if (palier.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new palier cannot already have an ID")).body(null);
        }
        Palier result = palierRepository.save(palier);
        palierSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/paliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paliers : Updates an existing palier.
     *
     * @param palier the palier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated palier,
     * or with status 400 (Bad Request) if the palier is not valid,
     * or with status 500 (Internal Server Error) if the palier couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paliers")
    @Timed
    public ResponseEntity<Palier> updatePalier(@RequestBody Palier palier) throws URISyntaxException {
        log.debug("REST request to update Palier : {}", palier);
        if (palier.getId() == null) {
            return createPalier(palier);
        }
        Palier result = palierRepository.save(palier);
        palierSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, palier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paliers : get all the paliers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paliers in body
     */
    @GetMapping("/paliers")
    @Timed
    public List<Palier> getAllPaliers() {
        log.debug("REST request to get all Paliers");
        List<Palier> paliers = palierRepository.findAll();
        return paliers;
    }

    /**
     * GET  /paliers/:id : get the "id" palier.
     *
     * @param id the id of the palier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the palier, or with status 404 (Not Found)
     */
    @GetMapping("/paliers/{id}")
    @Timed
    public ResponseEntity<Palier> getPalier(@PathVariable Long id) {
        log.debug("REST request to get Palier : {}", id);
        Palier palier = palierRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(palier));
    }

    /**
     * DELETE  /paliers/:id : delete the "id" palier.
     *
     * @param id the id of the palier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paliers/{id}")
    @Timed
    public ResponseEntity<Void> deletePalier(@PathVariable Long id) {
        log.debug("REST request to delete Palier : {}", id);
        palierRepository.delete(id);
        palierSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/paliers?query=:query : search for the palier corresponding
     * to the query.
     *
     * @param query the query of the palier search 
     * @return the result of the search
     */
    @GetMapping("/_search/paliers")
    @Timed
    public List<Palier> searchPaliers(@RequestParam String query) {
        log.debug("REST request to search Paliers for query {}", query);
        return StreamSupport
            .stream(palierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Gouvernorat;

import com.agrobourse.dev.repository.GouvernoratRepository;
import com.agrobourse.dev.repository.search.GouvernoratSearchRepository;
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
 * REST controller for managing Gouvernorat.
 */
@RestController
@RequestMapping("/api")
public class GouvernoratResource {

    private final Logger log = LoggerFactory.getLogger(GouvernoratResource.class);

    private static final String ENTITY_NAME = "gouvernorat";
        
    private final GouvernoratRepository gouvernoratRepository;

    private final GouvernoratSearchRepository gouvernoratSearchRepository;

    public GouvernoratResource(GouvernoratRepository gouvernoratRepository, GouvernoratSearchRepository gouvernoratSearchRepository) {
        this.gouvernoratRepository = gouvernoratRepository;
        this.gouvernoratSearchRepository = gouvernoratSearchRepository;
    }

    /**
     * POST  /gouvernorats : Create a new gouvernorat.
     *
     * @param gouvernorat the gouvernorat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gouvernorat, or with status 400 (Bad Request) if the gouvernorat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gouvernorats")
    @Timed
    public ResponseEntity<Gouvernorat> createGouvernorat(@RequestBody Gouvernorat gouvernorat) throws URISyntaxException {
        log.debug("REST request to save Gouvernorat : {}", gouvernorat);
        if (gouvernorat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gouvernorat cannot already have an ID")).body(null);
        }
        Gouvernorat result = gouvernoratRepository.save(gouvernorat);
        gouvernoratSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/gouvernorats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gouvernorats : Updates an existing gouvernorat.
     *
     * @param gouvernorat the gouvernorat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gouvernorat,
     * or with status 400 (Bad Request) if the gouvernorat is not valid,
     * or with status 500 (Internal Server Error) if the gouvernorat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gouvernorats")
    @Timed
    public ResponseEntity<Gouvernorat> updateGouvernorat(@RequestBody Gouvernorat gouvernorat) throws URISyntaxException {
        log.debug("REST request to update Gouvernorat : {}", gouvernorat);
        if (gouvernorat.getId() == null) {
            return createGouvernorat(gouvernorat);
        }
        Gouvernorat result = gouvernoratRepository.save(gouvernorat);
        gouvernoratSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gouvernorat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gouvernorats : get all the gouvernorats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gouvernorats in body
     */
    @GetMapping("/gouvernorats")
    @Timed
    public List<Gouvernorat> getAllGouvernorats() {
        log.debug("REST request to get all Gouvernorats");
        List<Gouvernorat> gouvernorats = gouvernoratRepository.findAll();
        return gouvernorats;
    }

    /**
     * GET  /gouvernorats/:id : get the "id" gouvernorat.
     *
     * @param id the id of the gouvernorat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gouvernorat, or with status 404 (Not Found)
     */
    @GetMapping("/gouvernorats/{id}")
    @Timed
    public ResponseEntity<Gouvernorat> getGouvernorat(@PathVariable Long id) {
        log.debug("REST request to get Gouvernorat : {}", id);
        Gouvernorat gouvernorat = gouvernoratRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gouvernorat));
    }

    /**
     * DELETE  /gouvernorats/:id : delete the "id" gouvernorat.
     *
     * @param id the id of the gouvernorat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gouvernorats/{id}")
    @Timed
    public ResponseEntity<Void> deleteGouvernorat(@PathVariable Long id) {
        log.debug("REST request to delete Gouvernorat : {}", id);
        gouvernoratRepository.delete(id);
        gouvernoratSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/gouvernorats?query=:query : search for the gouvernorat corresponding
     * to the query.
     *
     * @param query the query of the gouvernorat search 
     * @return the result of the search
     */
    @GetMapping("/_search/gouvernorats")
    @Timed
    public List<Gouvernorat> searchGouvernorats(@RequestParam String query) {
        log.debug("REST request to search Gouvernorats for query {}", query);
        return StreamSupport
            .stream(gouvernoratSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

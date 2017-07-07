package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Localite;

import com.agrobourse.dev.repository.LocaliteRepository;
import com.agrobourse.dev.repository.search.LocaliteSearchRepository;
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
 * REST controller for managing Localite.
 */
@RestController
@RequestMapping("/api")
public class LocaliteResource {

    private final Logger log = LoggerFactory.getLogger(LocaliteResource.class);

    private static final String ENTITY_NAME = "localite";
        
    private final LocaliteRepository localiteRepository;

    private final LocaliteSearchRepository localiteSearchRepository;

    public LocaliteResource(LocaliteRepository localiteRepository, LocaliteSearchRepository localiteSearchRepository) {
        this.localiteRepository = localiteRepository;
        this.localiteSearchRepository = localiteSearchRepository;
    }

    /**
     * POST  /localites : Create a new localite.
     *
     * @param localite the localite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localite, or with status 400 (Bad Request) if the localite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localites")
    @Timed
    public ResponseEntity<Localite> createLocalite(@RequestBody Localite localite) throws URISyntaxException {
        log.debug("REST request to save Localite : {}", localite);
        if (localite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new localite cannot already have an ID")).body(null);
        }
        Localite result = localiteRepository.save(localite);
        localiteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/localites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localites : Updates an existing localite.
     *
     * @param localite the localite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localite,
     * or with status 400 (Bad Request) if the localite is not valid,
     * or with status 500 (Internal Server Error) if the localite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localites")
    @Timed
    public ResponseEntity<Localite> updateLocalite(@RequestBody Localite localite) throws URISyntaxException {
        log.debug("REST request to update Localite : {}", localite);
        if (localite.getId() == null) {
            return createLocalite(localite);
        }
        Localite result = localiteRepository.save(localite);
        localiteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localites : get all the localites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of localites in body
     */
    @GetMapping("/localites")
    @Timed
    public List<Localite> getAllLocalites() {
        log.debug("REST request to get all Localites");
        List<Localite> localites = localiteRepository.findAll();
        return localites;
    }

    /**
     * GET  /localites/:id : get the "id" localite.
     *
     * @param id the id of the localite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localite, or with status 404 (Not Found)
     */
    @GetMapping("/localites/{id}")
    @Timed
    public ResponseEntity<Localite> getLocalite(@PathVariable Long id) {
        log.debug("REST request to get Localite : {}", id);
        Localite localite = localiteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localite));
    }

    /**
     * DELETE  /localites/:id : delete the "id" localite.
     *
     * @param id the id of the localite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localites/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalite(@PathVariable Long id) {
        log.debug("REST request to delete Localite : {}", id);
        localiteRepository.delete(id);
        localiteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/localites?query=:query : search for the localite corresponding
     * to the query.
     *
     * @param query the query of the localite search 
     * @return the result of the search
     */
    @GetMapping("/_search/localites")
    @Timed
    public List<Localite> searchLocalites(@RequestParam String query) {
        log.debug("REST request to search Localites for query {}", query);
        return StreamSupport
            .stream(localiteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.EmpActualite;

import com.agrobourse.dev.repository.EmpActualiteRepository;
import com.agrobourse.dev.repository.search.EmpActualiteSearchRepository;
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
 * REST controller for managing EmpActualite.
 */
@RestController
@RequestMapping("/api")
public class EmpActualiteResource {

    private final Logger log = LoggerFactory.getLogger(EmpActualiteResource.class);

    private static final String ENTITY_NAME = "empActualite";
        
    private final EmpActualiteRepository empActualiteRepository;

    private final EmpActualiteSearchRepository empActualiteSearchRepository;

    public EmpActualiteResource(EmpActualiteRepository empActualiteRepository, EmpActualiteSearchRepository empActualiteSearchRepository) {
        this.empActualiteRepository = empActualiteRepository;
        this.empActualiteSearchRepository = empActualiteSearchRepository;
    }

    /**
     * POST  /emp-actualites : Create a new empActualite.
     *
     * @param empActualite the empActualite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empActualite, or with status 400 (Bad Request) if the empActualite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emp-actualites")
    @Timed
    public ResponseEntity<EmpActualite> createEmpActualite(@RequestBody EmpActualite empActualite) throws URISyntaxException {
        log.debug("REST request to save EmpActualite : {}", empActualite);
        if (empActualite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new empActualite cannot already have an ID")).body(null);
        }
        EmpActualite result = empActualiteRepository.save(empActualite);
        empActualiteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/emp-actualites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emp-actualites : Updates an existing empActualite.
     *
     * @param empActualite the empActualite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empActualite,
     * or with status 400 (Bad Request) if the empActualite is not valid,
     * or with status 500 (Internal Server Error) if the empActualite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emp-actualites")
    @Timed
    public ResponseEntity<EmpActualite> updateEmpActualite(@RequestBody EmpActualite empActualite) throws URISyntaxException {
        log.debug("REST request to update EmpActualite : {}", empActualite);
        if (empActualite.getId() == null) {
            return createEmpActualite(empActualite);
        }
        EmpActualite result = empActualiteRepository.save(empActualite);
        empActualiteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empActualite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emp-actualites : get all the empActualites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of empActualites in body
     */
    @GetMapping("/emp-actualites")
    @Timed
    public List<EmpActualite> getAllEmpActualites() {
        log.debug("REST request to get all EmpActualites");
        List<EmpActualite> empActualites = empActualiteRepository.findAllWithEagerRelationships();
        return empActualites;
    }

    /**
     * GET  /emp-actualites/:id : get the "id" empActualite.
     *
     * @param id the id of the empActualite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empActualite, or with status 404 (Not Found)
     */
    @GetMapping("/emp-actualites/{id}")
    @Timed
    public ResponseEntity<EmpActualite> getEmpActualite(@PathVariable Long id) {
        log.debug("REST request to get EmpActualite : {}", id);
        EmpActualite empActualite = empActualiteRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(empActualite));
    }

    /**
     * DELETE  /emp-actualites/:id : delete the "id" empActualite.
     *
     * @param id the id of the empActualite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emp-actualites/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpActualite(@PathVariable Long id) {
        log.debug("REST request to delete EmpActualite : {}", id);
        empActualiteRepository.delete(id);
        empActualiteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/emp-actualites?query=:query : search for the empActualite corresponding
     * to the query.
     *
     * @param query the query of the empActualite search 
     * @return the result of the search
     */
    @GetMapping("/_search/emp-actualites")
    @Timed
    public List<EmpActualite> searchEmpActualites(@RequestParam String query) {
        log.debug("REST request to search EmpActualites for query {}", query);
        return StreamSupport
            .stream(empActualiteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

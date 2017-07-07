package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.EmpAnnonce;

import com.agrobourse.dev.repository.EmpAnnonceRepository;
import com.agrobourse.dev.repository.search.EmpAnnonceSearchRepository;
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
 * REST controller for managing EmpAnnonce.
 */
@RestController
@RequestMapping("/api")
public class EmpAnnonceResource {

    private final Logger log = LoggerFactory.getLogger(EmpAnnonceResource.class);

    private static final String ENTITY_NAME = "empAnnonce";
        
    private final EmpAnnonceRepository empAnnonceRepository;

    private final EmpAnnonceSearchRepository empAnnonceSearchRepository;

    public EmpAnnonceResource(EmpAnnonceRepository empAnnonceRepository, EmpAnnonceSearchRepository empAnnonceSearchRepository) {
        this.empAnnonceRepository = empAnnonceRepository;
        this.empAnnonceSearchRepository = empAnnonceSearchRepository;
    }

    /**
     * POST  /emp-annonces : Create a new empAnnonce.
     *
     * @param empAnnonce the empAnnonce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empAnnonce, or with status 400 (Bad Request) if the empAnnonce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emp-annonces")
    @Timed
    public ResponseEntity<EmpAnnonce> createEmpAnnonce(@RequestBody EmpAnnonce empAnnonce) throws URISyntaxException {
        log.debug("REST request to save EmpAnnonce : {}", empAnnonce);
        if (empAnnonce.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new empAnnonce cannot already have an ID")).body(null);
        }
        EmpAnnonce result = empAnnonceRepository.save(empAnnonce);
        empAnnonceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/emp-annonces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emp-annonces : Updates an existing empAnnonce.
     *
     * @param empAnnonce the empAnnonce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empAnnonce,
     * or with status 400 (Bad Request) if the empAnnonce is not valid,
     * or with status 500 (Internal Server Error) if the empAnnonce couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emp-annonces")
    @Timed
    public ResponseEntity<EmpAnnonce> updateEmpAnnonce(@RequestBody EmpAnnonce empAnnonce) throws URISyntaxException {
        log.debug("REST request to update EmpAnnonce : {}", empAnnonce);
        if (empAnnonce.getId() == null) {
            return createEmpAnnonce(empAnnonce);
        }
        EmpAnnonce result = empAnnonceRepository.save(empAnnonce);
        empAnnonceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empAnnonce.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emp-annonces : get all the empAnnonces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of empAnnonces in body
     */
    @GetMapping("/emp-annonces")
    @Timed
    public List<EmpAnnonce> getAllEmpAnnonces() {
        log.debug("REST request to get all EmpAnnonces");
        List<EmpAnnonce> empAnnonces = empAnnonceRepository.findAllWithEagerRelationships();
        return empAnnonces;
    }

    /**
     * GET  /emp-annonces/:id : get the "id" empAnnonce.
     *
     * @param id the id of the empAnnonce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empAnnonce, or with status 404 (Not Found)
     */
    @GetMapping("/emp-annonces/{id}")
    @Timed
    public ResponseEntity<EmpAnnonce> getEmpAnnonce(@PathVariable Long id) {
        log.debug("REST request to get EmpAnnonce : {}", id);
        EmpAnnonce empAnnonce = empAnnonceRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(empAnnonce));
    }

    /**
     * DELETE  /emp-annonces/:id : delete the "id" empAnnonce.
     *
     * @param id the id of the empAnnonce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emp-annonces/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpAnnonce(@PathVariable Long id) {
        log.debug("REST request to delete EmpAnnonce : {}", id);
        empAnnonceRepository.delete(id);
        empAnnonceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/emp-annonces?query=:query : search for the empAnnonce corresponding
     * to the query.
     *
     * @param query the query of the empAnnonce search 
     * @return the result of the search
     */
    @GetMapping("/_search/emp-annonces")
    @Timed
    public List<EmpAnnonce> searchEmpAnnonces(@RequestParam String query) {
        log.debug("REST request to search EmpAnnonces for query {}", query);
        return StreamSupport
            .stream(empAnnonceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

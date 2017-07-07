package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.EmpCV;

import com.agrobourse.dev.repository.EmpCVRepository;
import com.agrobourse.dev.repository.search.EmpCVSearchRepository;
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
 * REST controller for managing EmpCV.
 */
@RestController
@RequestMapping("/api")
public class EmpCVResource {

    private final Logger log = LoggerFactory.getLogger(EmpCVResource.class);

    private static final String ENTITY_NAME = "empCV";
        
    private final EmpCVRepository empCVRepository;

    private final EmpCVSearchRepository empCVSearchRepository;

    public EmpCVResource(EmpCVRepository empCVRepository, EmpCVSearchRepository empCVSearchRepository) {
        this.empCVRepository = empCVRepository;
        this.empCVSearchRepository = empCVSearchRepository;
    }

    /**
     * POST  /emp-cvs : Create a new empCV.
     *
     * @param empCV the empCV to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empCV, or with status 400 (Bad Request) if the empCV has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emp-cvs")
    @Timed
    public ResponseEntity<EmpCV> createEmpCV(@RequestBody EmpCV empCV) throws URISyntaxException {
        log.debug("REST request to save EmpCV : {}", empCV);
        if (empCV.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new empCV cannot already have an ID")).body(null);
        }
        EmpCV result = empCVRepository.save(empCV);
        empCVSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/emp-cvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emp-cvs : Updates an existing empCV.
     *
     * @param empCV the empCV to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empCV,
     * or with status 400 (Bad Request) if the empCV is not valid,
     * or with status 500 (Internal Server Error) if the empCV couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emp-cvs")
    @Timed
    public ResponseEntity<EmpCV> updateEmpCV(@RequestBody EmpCV empCV) throws URISyntaxException {
        log.debug("REST request to update EmpCV : {}", empCV);
        if (empCV.getId() == null) {
            return createEmpCV(empCV);
        }
        EmpCV result = empCVRepository.save(empCV);
        empCVSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empCV.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emp-cvs : get all the empCVS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of empCVS in body
     */
    @GetMapping("/emp-cvs")
    @Timed
    public List<EmpCV> getAllEmpCVS() {
        log.debug("REST request to get all EmpCVS");
        List<EmpCV> empCVS = empCVRepository.findAllWithEagerRelationships();
        return empCVS;
    }

    /**
     * GET  /emp-cvs/:id : get the "id" empCV.
     *
     * @param id the id of the empCV to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empCV, or with status 404 (Not Found)
     */
    @GetMapping("/emp-cvs/{id}")
    @Timed
    public ResponseEntity<EmpCV> getEmpCV(@PathVariable Long id) {
        log.debug("REST request to get EmpCV : {}", id);
        EmpCV empCV = empCVRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(empCV));
    }

    /**
     * DELETE  /emp-cvs/:id : delete the "id" empCV.
     *
     * @param id the id of the empCV to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emp-cvs/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpCV(@PathVariable Long id) {
        log.debug("REST request to delete EmpCV : {}", id);
        empCVRepository.delete(id);
        empCVSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/emp-cvs?query=:query : search for the empCV corresponding
     * to the query.
     *
     * @param query the query of the empCV search 
     * @return the result of the search
     */
    @GetMapping("/_search/emp-cvs")
    @Timed
    public List<EmpCV> searchEmpCVS(@RequestParam String query) {
        log.debug("REST request to search EmpCVS for query {}", query);
        return StreamSupport
            .stream(empCVSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.GPO;

import com.agrobourse.dev.repository.GPORepository;
import com.agrobourse.dev.repository.search.GPOSearchRepository;
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
 * REST controller for managing GPO.
 */
@RestController
@RequestMapping("/api")
public class GPOResource {

    private final Logger log = LoggerFactory.getLogger(GPOResource.class);

    private static final String ENTITY_NAME = "gPO";
        
    private final GPORepository gPORepository;

    private final GPOSearchRepository gPOSearchRepository;

    public GPOResource(GPORepository gPORepository, GPOSearchRepository gPOSearchRepository) {
        this.gPORepository = gPORepository;
        this.gPOSearchRepository = gPOSearchRepository;
    }

    /**
     * POST  /g-pos : Create a new gPO.
     *
     * @param gPO the gPO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gPO, or with status 400 (Bad Request) if the gPO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/g-pos")
    @Timed
    public ResponseEntity<GPO> createGPO(@RequestBody GPO gPO) throws URISyntaxException {
        log.debug("REST request to save GPO : {}", gPO);
        if (gPO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gPO cannot already have an ID")).body(null);
        }
        GPO result = gPORepository.save(gPO);
        gPOSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/g-pos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /g-pos : Updates an existing gPO.
     *
     * @param gPO the gPO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gPO,
     * or with status 400 (Bad Request) if the gPO is not valid,
     * or with status 500 (Internal Server Error) if the gPO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/g-pos")
    @Timed
    public ResponseEntity<GPO> updateGPO(@RequestBody GPO gPO) throws URISyntaxException {
        log.debug("REST request to update GPO : {}", gPO);
        if (gPO.getId() == null) {
            return createGPO(gPO);
        }
        GPO result = gPORepository.save(gPO);
        gPOSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gPO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /g-pos : get all the gPOS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gPOS in body
     */
    @GetMapping("/g-pos")
    @Timed
    public List<GPO> getAllGPOS() {
        log.debug("REST request to get all GPOS");
        List<GPO> gPOS = gPORepository.findAll();
        return gPOS;
    }

    /**
     * GET  /g-pos/:id : get the "id" gPO.
     *
     * @param id the id of the gPO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gPO, or with status 404 (Not Found)
     */
    @GetMapping("/g-pos/{id}")
    @Timed
    public ResponseEntity<GPO> getGPO(@PathVariable Long id) {
        log.debug("REST request to get GPO : {}", id);
        GPO gPO = gPORepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gPO));
    }

    /**
     * DELETE  /g-pos/:id : delete the "id" gPO.
     *
     * @param id the id of the gPO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/g-pos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGPO(@PathVariable Long id) {
        log.debug("REST request to delete GPO : {}", id);
        gPORepository.delete(id);
        gPOSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/g-pos?query=:query : search for the gPO corresponding
     * to the query.
     *
     * @param query the query of the gPO search 
     * @return the result of the search
     */
    @GetMapping("/_search/g-pos")
    @Timed
    public List<GPO> searchGPOS(@RequestParam String query) {
        log.debug("REST request to search GPOS for query {}", query);
        return StreamSupport
            .stream(gPOSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

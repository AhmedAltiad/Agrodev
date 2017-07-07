package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.TraderCA;

import com.agrobourse.dev.repository.TraderCARepository;
import com.agrobourse.dev.repository.search.TraderCASearchRepository;
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
 * REST controller for managing TraderCA.
 */
@RestController
@RequestMapping("/api")
public class TraderCAResource {

    private final Logger log = LoggerFactory.getLogger(TraderCAResource.class);

    private static final String ENTITY_NAME = "traderCA";
        
    private final TraderCARepository traderCARepository;

    private final TraderCASearchRepository traderCASearchRepository;

    public TraderCAResource(TraderCARepository traderCARepository, TraderCASearchRepository traderCASearchRepository) {
        this.traderCARepository = traderCARepository;
        this.traderCASearchRepository = traderCASearchRepository;
    }

    /**
     * POST  /trader-cas : Create a new traderCA.
     *
     * @param traderCA the traderCA to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traderCA, or with status 400 (Bad Request) if the traderCA has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trader-cas")
    @Timed
    public ResponseEntity<TraderCA> createTraderCA(@RequestBody TraderCA traderCA) throws URISyntaxException {
        log.debug("REST request to save TraderCA : {}", traderCA);
        if (traderCA.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traderCA cannot already have an ID")).body(null);
        }
        TraderCA result = traderCARepository.save(traderCA);
        traderCASearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trader-cas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trader-cas : Updates an existing traderCA.
     *
     * @param traderCA the traderCA to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traderCA,
     * or with status 400 (Bad Request) if the traderCA is not valid,
     * or with status 500 (Internal Server Error) if the traderCA couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trader-cas")
    @Timed
    public ResponseEntity<TraderCA> updateTraderCA(@RequestBody TraderCA traderCA) throws URISyntaxException {
        log.debug("REST request to update TraderCA : {}", traderCA);
        if (traderCA.getId() == null) {
            return createTraderCA(traderCA);
        }
        TraderCA result = traderCARepository.save(traderCA);
        traderCASearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traderCA.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trader-cas : get all the traderCAS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traderCAS in body
     */
    @GetMapping("/trader-cas")
    @Timed
    public List<TraderCA> getAllTraderCAS() {
        log.debug("REST request to get all TraderCAS");
        List<TraderCA> traderCAS = traderCARepository.findAll();
        return traderCAS;
    }

    /**
     * GET  /trader-cas/:id : get the "id" traderCA.
     *
     * @param id the id of the traderCA to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traderCA, or with status 404 (Not Found)
     */
    @GetMapping("/trader-cas/{id}")
    @Timed
    public ResponseEntity<TraderCA> getTraderCA(@PathVariable Long id) {
        log.debug("REST request to get TraderCA : {}", id);
        TraderCA traderCA = traderCARepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traderCA));
    }

    /**
     * DELETE  /trader-cas/:id : delete the "id" traderCA.
     *
     * @param id the id of the traderCA to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trader-cas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraderCA(@PathVariable Long id) {
        log.debug("REST request to delete TraderCA : {}", id);
        traderCARepository.delete(id);
        traderCASearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/trader-cas?query=:query : search for the traderCA corresponding
     * to the query.
     *
     * @param query the query of the traderCA search 
     * @return the result of the search
     */
    @GetMapping("/_search/trader-cas")
    @Timed
    public List<TraderCA> searchTraderCAS(@RequestParam String query) {
        log.debug("REST request to search TraderCAS for query {}", query);
        return StreamSupport
            .stream(traderCASearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

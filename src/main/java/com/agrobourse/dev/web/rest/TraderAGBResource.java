package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.TraderAGB;

import com.agrobourse.dev.repository.TraderAGBRepository;
import com.agrobourse.dev.repository.search.TraderAGBSearchRepository;
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
 * REST controller for managing TraderAGB.
 */
@RestController
@RequestMapping("/api")
public class TraderAGBResource {

    private final Logger log = LoggerFactory.getLogger(TraderAGBResource.class);

    private static final String ENTITY_NAME = "traderAGB";
        
    private final TraderAGBRepository traderAGBRepository;

    private final TraderAGBSearchRepository traderAGBSearchRepository;

    public TraderAGBResource(TraderAGBRepository traderAGBRepository, TraderAGBSearchRepository traderAGBSearchRepository) {
        this.traderAGBRepository = traderAGBRepository;
        this.traderAGBSearchRepository = traderAGBSearchRepository;
    }

    /**
     * POST  /trader-agbs : Create a new traderAGB.
     *
     * @param traderAGB the traderAGB to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traderAGB, or with status 400 (Bad Request) if the traderAGB has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trader-agbs")
    @Timed
    public ResponseEntity<TraderAGB> createTraderAGB(@RequestBody TraderAGB traderAGB) throws URISyntaxException {
        log.debug("REST request to save TraderAGB : {}", traderAGB);
        if (traderAGB.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traderAGB cannot already have an ID")).body(null);
        }
        TraderAGB result = traderAGBRepository.save(traderAGB);
        traderAGBSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trader-agbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trader-agbs : Updates an existing traderAGB.
     *
     * @param traderAGB the traderAGB to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traderAGB,
     * or with status 400 (Bad Request) if the traderAGB is not valid,
     * or with status 500 (Internal Server Error) if the traderAGB couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trader-agbs")
    @Timed
    public ResponseEntity<TraderAGB> updateTraderAGB(@RequestBody TraderAGB traderAGB) throws URISyntaxException {
        log.debug("REST request to update TraderAGB : {}", traderAGB);
        if (traderAGB.getId() == null) {
            return createTraderAGB(traderAGB);
        }
        TraderAGB result = traderAGBRepository.save(traderAGB);
        traderAGBSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traderAGB.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trader-agbs : get all the traderAGBS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traderAGBS in body
     */
    @GetMapping("/trader-agbs")
    @Timed
    public List<TraderAGB> getAllTraderAGBS() {
        log.debug("REST request to get all TraderAGBS");
        List<TraderAGB> traderAGBS = traderAGBRepository.findAll();
        return traderAGBS;
    }

    /**
     * GET  /trader-agbs/:id : get the "id" traderAGB.
     *
     * @param id the id of the traderAGB to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traderAGB, or with status 404 (Not Found)
     */
    @GetMapping("/trader-agbs/{id}")
    @Timed
    public ResponseEntity<TraderAGB> getTraderAGB(@PathVariable Long id) {
        log.debug("REST request to get TraderAGB : {}", id);
        TraderAGB traderAGB = traderAGBRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traderAGB));
    }

    /**
     * DELETE  /trader-agbs/:id : delete the "id" traderAGB.
     *
     * @param id the id of the traderAGB to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trader-agbs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraderAGB(@PathVariable Long id) {
        log.debug("REST request to delete TraderAGB : {}", id);
        traderAGBRepository.delete(id);
        traderAGBSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/trader-agbs?query=:query : search for the traderAGB corresponding
     * to the query.
     *
     * @param query the query of the traderAGB search 
     * @return the result of the search
     */
    @GetMapping("/_search/trader-agbs")
    @Timed
    public List<TraderAGB> searchTraderAGBS(@RequestParam String query) {
        log.debug("REST request to search TraderAGBS for query {}", query);
        return StreamSupport
            .stream(traderAGBSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

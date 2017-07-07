package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Production;

import com.agrobourse.dev.repository.ProductionRepository;
import com.agrobourse.dev.repository.search.ProductionSearchRepository;
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
 * REST controller for managing Production.
 */
@RestController
@RequestMapping("/api")
public class ProductionResource {

    private final Logger log = LoggerFactory.getLogger(ProductionResource.class);

    private static final String ENTITY_NAME = "production";
        
    private final ProductionRepository productionRepository;

    private final ProductionSearchRepository productionSearchRepository;

    public ProductionResource(ProductionRepository productionRepository, ProductionSearchRepository productionSearchRepository) {
        this.productionRepository = productionRepository;
        this.productionSearchRepository = productionSearchRepository;
    }

    /**
     * POST  /productions : Create a new production.
     *
     * @param production the production to create
     * @return the ResponseEntity with status 201 (Created) and with body the new production, or with status 400 (Bad Request) if the production has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productions")
    @Timed
    public ResponseEntity<Production> createProduction(@RequestBody Production production) throws URISyntaxException {
        log.debug("REST request to save Production : {}", production);
        if (production.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new production cannot already have an ID")).body(null);
        }
        Production result = productionRepository.save(production);
        productionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productions : Updates an existing production.
     *
     * @param production the production to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated production,
     * or with status 400 (Bad Request) if the production is not valid,
     * or with status 500 (Internal Server Error) if the production couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productions")
    @Timed
    public ResponseEntity<Production> updateProduction(@RequestBody Production production) throws URISyntaxException {
        log.debug("REST request to update Production : {}", production);
        if (production.getId() == null) {
            return createProduction(production);
        }
        Production result = productionRepository.save(production);
        productionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, production.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productions : get all the productions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productions in body
     */
    @GetMapping("/productions")
    @Timed
    public List<Production> getAllProductions() {
        log.debug("REST request to get all Productions");
        List<Production> productions = productionRepository.findAll();
        return productions;
    }

    /**
     * GET  /productions/:id : get the "id" production.
     *
     * @param id the id of the production to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the production, or with status 404 (Not Found)
     */
    @GetMapping("/productions/{id}")
    @Timed
    public ResponseEntity<Production> getProduction(@PathVariable Long id) {
        log.debug("REST request to get Production : {}", id);
        Production production = productionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(production));
    }

    /**
     * DELETE  /productions/:id : delete the "id" production.
     *
     * @param id the id of the production to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productions/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduction(@PathVariable Long id) {
        log.debug("REST request to delete Production : {}", id);
        productionRepository.delete(id);
        productionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/productions?query=:query : search for the production corresponding
     * to the query.
     *
     * @param query the query of the production search 
     * @return the result of the search
     */
    @GetMapping("/_search/productions")
    @Timed
    public List<Production> searchProductions(@RequestParam String query) {
        log.debug("REST request to search Productions for query {}", query);
        return StreamSupport
            .stream(productionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

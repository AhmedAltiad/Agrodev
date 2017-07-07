package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.TransactionHistorique;

import com.agrobourse.dev.repository.TransactionHistoriqueRepository;
import com.agrobourse.dev.repository.search.TransactionHistoriqueSearchRepository;
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
 * REST controller for managing TransactionHistorique.
 */
@RestController
@RequestMapping("/api")
public class TransactionHistoriqueResource {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoriqueResource.class);

    private static final String ENTITY_NAME = "transactionHistorique";
        
    private final TransactionHistoriqueRepository transactionHistoriqueRepository;

    private final TransactionHistoriqueSearchRepository transactionHistoriqueSearchRepository;

    public TransactionHistoriqueResource(TransactionHistoriqueRepository transactionHistoriqueRepository, TransactionHistoriqueSearchRepository transactionHistoriqueSearchRepository) {
        this.transactionHistoriqueRepository = transactionHistoriqueRepository;
        this.transactionHistoriqueSearchRepository = transactionHistoriqueSearchRepository;
    }

    /**
     * POST  /transaction-historiques : Create a new transactionHistorique.
     *
     * @param transactionHistorique the transactionHistorique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionHistorique, or with status 400 (Bad Request) if the transactionHistorique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-historiques")
    @Timed
    public ResponseEntity<TransactionHistorique> createTransactionHistorique(@RequestBody TransactionHistorique transactionHistorique) throws URISyntaxException {
        log.debug("REST request to save TransactionHistorique : {}", transactionHistorique);
        if (transactionHistorique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transactionHistorique cannot already have an ID")).body(null);
        }
        TransactionHistorique result = transactionHistoriqueRepository.save(transactionHistorique);
        transactionHistoriqueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transaction-historiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-historiques : Updates an existing transactionHistorique.
     *
     * @param transactionHistorique the transactionHistorique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionHistorique,
     * or with status 400 (Bad Request) if the transactionHistorique is not valid,
     * or with status 500 (Internal Server Error) if the transactionHistorique couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-historiques")
    @Timed
    public ResponseEntity<TransactionHistorique> updateTransactionHistorique(@RequestBody TransactionHistorique transactionHistorique) throws URISyntaxException {
        log.debug("REST request to update TransactionHistorique : {}", transactionHistorique);
        if (transactionHistorique.getId() == null) {
            return createTransactionHistorique(transactionHistorique);
        }
        TransactionHistorique result = transactionHistoriqueRepository.save(transactionHistorique);
        transactionHistoriqueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionHistorique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-historiques : get all the transactionHistoriques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactionHistoriques in body
     */
    @GetMapping("/transaction-historiques")
    @Timed
    public List<TransactionHistorique> getAllTransactionHistoriques() {
        log.debug("REST request to get all TransactionHistoriques");
        List<TransactionHistorique> transactionHistoriques = transactionHistoriqueRepository.findAll();
        return transactionHistoriques;
    }

    /**
     * GET  /transaction-historiques/:id : get the "id" transactionHistorique.
     *
     * @param id the id of the transactionHistorique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionHistorique, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-historiques/{id}")
    @Timed
    public ResponseEntity<TransactionHistorique> getTransactionHistorique(@PathVariable Long id) {
        log.debug("REST request to get TransactionHistorique : {}", id);
        TransactionHistorique transactionHistorique = transactionHistoriqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionHistorique));
    }

    /**
     * DELETE  /transaction-historiques/:id : delete the "id" transactionHistorique.
     *
     * @param id the id of the transactionHistorique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-historiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionHistorique(@PathVariable Long id) {
        log.debug("REST request to delete TransactionHistorique : {}", id);
        transactionHistoriqueRepository.delete(id);
        transactionHistoriqueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transaction-historiques?query=:query : search for the transactionHistorique corresponding
     * to the query.
     *
     * @param query the query of the transactionHistorique search 
     * @return the result of the search
     */
    @GetMapping("/_search/transaction-historiques")
    @Timed
    public List<TransactionHistorique> searchTransactionHistoriques(@RequestParam String query) {
        log.debug("REST request to search TransactionHistoriques for query {}", query);
        return StreamSupport
            .stream(transactionHistoriqueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

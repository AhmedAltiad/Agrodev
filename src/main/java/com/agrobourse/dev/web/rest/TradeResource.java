package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Trade;

import com.agrobourse.dev.repository.TradeRepository;
import com.agrobourse.dev.repository.search.TradeSearchRepository;
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
 * REST controller for managing Trade.
 */
@RestController
@RequestMapping("/api")
public class TradeResource {

    private final Logger log = LoggerFactory.getLogger(TradeResource.class);

    private static final String ENTITY_NAME = "trade";
        
    private final TradeRepository tradeRepository;

    private final TradeSearchRepository tradeSearchRepository;

    public TradeResource(TradeRepository tradeRepository, TradeSearchRepository tradeSearchRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeSearchRepository = tradeSearchRepository;
    }

    /**
     * POST  /trades : Create a new trade.
     *
     * @param trade the trade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trade, or with status 400 (Bad Request) if the trade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trades")
    @Timed
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to save Trade : {}", trade);
        if (trade.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new trade cannot already have an ID")).body(null);
        }
        Trade result = tradeRepository.save(trade);
        tradeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trades : Updates an existing trade.
     *
     * @param trade the trade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trade,
     * or with status 400 (Bad Request) if the trade is not valid,
     * or with status 500 (Internal Server Error) if the trade couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trades")
    @Timed
    public ResponseEntity<Trade> updateTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to update Trade : {}", trade);
        if (trade.getId() == null) {
            return createTrade(trade);
        }
        Trade result = tradeRepository.save(trade);
        tradeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trades : get all the trades.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trades in body
     */
    @GetMapping("/trades")
    @Timed
    public List<Trade> getAllTrades() {
        log.debug("REST request to get all Trades");
        List<Trade> trades = tradeRepository.findAll();
        return trades;
    }

    /**
     * GET  /trades/:id : get the "id" trade.
     *
     * @param id the id of the trade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trade, or with status 404 (Not Found)
     */
    @GetMapping("/trades/{id}")
    @Timed
    public ResponseEntity<Trade> getTrade(@PathVariable Long id) {
        log.debug("REST request to get Trade : {}", id);
        Trade trade = tradeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trade));
    }

    /**
     * DELETE  /trades/:id : delete the "id" trade.
     *
     * @param id the id of the trade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trades/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        log.debug("REST request to delete Trade : {}", id);
        tradeRepository.delete(id);
        tradeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/trades?query=:query : search for the trade corresponding
     * to the query.
     *
     * @param query the query of the trade search 
     * @return the result of the search
     */
    @GetMapping("/_search/trades")
    @Timed
    public List<Trade> searchTrades(@RequestParam String query) {
        log.debug("REST request to search Trades for query {}", query);
        return StreamSupport
            .stream(tradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

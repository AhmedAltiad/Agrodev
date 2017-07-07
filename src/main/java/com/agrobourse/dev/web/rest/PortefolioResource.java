package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Portefolio;

import com.agrobourse.dev.repository.PortefolioRepository;
import com.agrobourse.dev.repository.search.PortefolioSearchRepository;
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
 * REST controller for managing Portefolio.
 */
@RestController
@RequestMapping("/api")
public class PortefolioResource {

    private final Logger log = LoggerFactory.getLogger(PortefolioResource.class);

    private static final String ENTITY_NAME = "portefolio";
        
    private final PortefolioRepository portefolioRepository;

    private final PortefolioSearchRepository portefolioSearchRepository;

    public PortefolioResource(PortefolioRepository portefolioRepository, PortefolioSearchRepository portefolioSearchRepository) {
        this.portefolioRepository = portefolioRepository;
        this.portefolioSearchRepository = portefolioSearchRepository;
    }

    /**
     * POST  /portefolios : Create a new portefolio.
     *
     * @param portefolio the portefolio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portefolio, or with status 400 (Bad Request) if the portefolio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/portefolios")
    @Timed
    public ResponseEntity<Portefolio> createPortefolio(@RequestBody Portefolio portefolio) throws URISyntaxException {
        log.debug("REST request to save Portefolio : {}", portefolio);
        if (portefolio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new portefolio cannot already have an ID")).body(null);
        }
        Portefolio result = portefolioRepository.save(portefolio);
        portefolioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/portefolios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /portefolios : Updates an existing portefolio.
     *
     * @param portefolio the portefolio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portefolio,
     * or with status 400 (Bad Request) if the portefolio is not valid,
     * or with status 500 (Internal Server Error) if the portefolio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/portefolios")
    @Timed
    public ResponseEntity<Portefolio> updatePortefolio(@RequestBody Portefolio portefolio) throws URISyntaxException {
        log.debug("REST request to update Portefolio : {}", portefolio);
        if (portefolio.getId() == null) {
            return createPortefolio(portefolio);
        }
        Portefolio result = portefolioRepository.save(portefolio);
        portefolioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, portefolio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /portefolios : get all the portefolios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of portefolios in body
     */
    @GetMapping("/portefolios")
    @Timed
    public List<Portefolio> getAllPortefolios() {
        log.debug("REST request to get all Portefolios");
        List<Portefolio> portefolios = portefolioRepository.findAllWithEagerRelationships();
        return portefolios;
    }

    /**
     * GET  /portefolios/:id : get the "id" portefolio.
     *
     * @param id the id of the portefolio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portefolio, or with status 404 (Not Found)
     */
    @GetMapping("/portefolios/{id}")
    @Timed
    public ResponseEntity<Portefolio> getPortefolio(@PathVariable Long id) {
        log.debug("REST request to get Portefolio : {}", id);
        Portefolio portefolio = portefolioRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(portefolio));
    }

    /**
     * DELETE  /portefolios/:id : delete the "id" portefolio.
     *
     * @param id the id of the portefolio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/portefolios/{id}")
    @Timed
    public ResponseEntity<Void> deletePortefolio(@PathVariable Long id) {
        log.debug("REST request to delete Portefolio : {}", id);
        portefolioRepository.delete(id);
        portefolioSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/portefolios?query=:query : search for the portefolio corresponding
     * to the query.
     *
     * @param query the query of the portefolio search 
     * @return the result of the search
     */
    @GetMapping("/_search/portefolios")
    @Timed
    public List<Portefolio> searchPortefolios(@RequestParam String query) {
        log.debug("REST request to search Portefolios for query {}", query);
        return StreamSupport
            .stream(portefolioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

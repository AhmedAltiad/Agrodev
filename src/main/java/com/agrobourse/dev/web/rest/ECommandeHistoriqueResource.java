package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.ECommandeHistorique;

import com.agrobourse.dev.repository.ECommandeHistoriqueRepository;
import com.agrobourse.dev.repository.search.ECommandeHistoriqueSearchRepository;
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
 * REST controller for managing ECommandeHistorique.
 */
@RestController
@RequestMapping("/api")
public class ECommandeHistoriqueResource {

    private final Logger log = LoggerFactory.getLogger(ECommandeHistoriqueResource.class);

    private static final String ENTITY_NAME = "eCommandeHistorique";
        
    private final ECommandeHistoriqueRepository eCommandeHistoriqueRepository;

    private final ECommandeHistoriqueSearchRepository eCommandeHistoriqueSearchRepository;

    public ECommandeHistoriqueResource(ECommandeHistoriqueRepository eCommandeHistoriqueRepository, ECommandeHistoriqueSearchRepository eCommandeHistoriqueSearchRepository) {
        this.eCommandeHistoriqueRepository = eCommandeHistoriqueRepository;
        this.eCommandeHistoriqueSearchRepository = eCommandeHistoriqueSearchRepository;
    }

    /**
     * POST  /e-commande-historiques : Create a new eCommandeHistorique.
     *
     * @param eCommandeHistorique the eCommandeHistorique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eCommandeHistorique, or with status 400 (Bad Request) if the eCommandeHistorique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-commande-historiques")
    @Timed
    public ResponseEntity<ECommandeHistorique> createECommandeHistorique(@RequestBody ECommandeHistorique eCommandeHistorique) throws URISyntaxException {
        log.debug("REST request to save ECommandeHistorique : {}", eCommandeHistorique);
        if (eCommandeHistorique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eCommandeHistorique cannot already have an ID")).body(null);
        }
        ECommandeHistorique result = eCommandeHistoriqueRepository.save(eCommandeHistorique);
        eCommandeHistoriqueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/e-commande-historiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-commande-historiques : Updates an existing eCommandeHistorique.
     *
     * @param eCommandeHistorique the eCommandeHistorique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eCommandeHistorique,
     * or with status 400 (Bad Request) if the eCommandeHistorique is not valid,
     * or with status 500 (Internal Server Error) if the eCommandeHistorique couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-commande-historiques")
    @Timed
    public ResponseEntity<ECommandeHistorique> updateECommandeHistorique(@RequestBody ECommandeHistorique eCommandeHistorique) throws URISyntaxException {
        log.debug("REST request to update ECommandeHistorique : {}", eCommandeHistorique);
        if (eCommandeHistorique.getId() == null) {
            return createECommandeHistorique(eCommandeHistorique);
        }
        ECommandeHistorique result = eCommandeHistoriqueRepository.save(eCommandeHistorique);
        eCommandeHistoriqueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eCommandeHistorique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-commande-historiques : get all the eCommandeHistoriques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eCommandeHistoriques in body
     */
    @GetMapping("/e-commande-historiques")
    @Timed
    public List<ECommandeHistorique> getAllECommandeHistoriques() {
        log.debug("REST request to get all ECommandeHistoriques");
        List<ECommandeHistorique> eCommandeHistoriques = eCommandeHistoriqueRepository.findAll();
        return eCommandeHistoriques;
    }

    /**
     * GET  /e-commande-historiques/:id : get the "id" eCommandeHistorique.
     *
     * @param id the id of the eCommandeHistorique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eCommandeHistorique, or with status 404 (Not Found)
     */
    @GetMapping("/e-commande-historiques/{id}")
    @Timed
    public ResponseEntity<ECommandeHistorique> getECommandeHistorique(@PathVariable Long id) {
        log.debug("REST request to get ECommandeHistorique : {}", id);
        ECommandeHistorique eCommandeHistorique = eCommandeHistoriqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eCommandeHistorique));
    }

    /**
     * DELETE  /e-commande-historiques/:id : delete the "id" eCommandeHistorique.
     *
     * @param id the id of the eCommandeHistorique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-commande-historiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteECommandeHistorique(@PathVariable Long id) {
        log.debug("REST request to delete ECommandeHistorique : {}", id);
        eCommandeHistoriqueRepository.delete(id);
        eCommandeHistoriqueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/e-commande-historiques?query=:query : search for the eCommandeHistorique corresponding
     * to the query.
     *
     * @param query the query of the eCommandeHistorique search 
     * @return the result of the search
     */
    @GetMapping("/_search/e-commande-historiques")
    @Timed
    public List<ECommandeHistorique> searchECommandeHistoriques(@RequestParam String query) {
        log.debug("REST request to search ECommandeHistoriques for query {}", query);
        return StreamSupport
            .stream(eCommandeHistoriqueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

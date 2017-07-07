package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.ECommande;

import com.agrobourse.dev.repository.ECommandeRepository;
import com.agrobourse.dev.repository.search.ECommandeSearchRepository;
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
 * REST controller for managing ECommande.
 */
@RestController
@RequestMapping("/api")
public class ECommandeResource {

    private final Logger log = LoggerFactory.getLogger(ECommandeResource.class);

    private static final String ENTITY_NAME = "eCommande";
        
    private final ECommandeRepository eCommandeRepository;

    private final ECommandeSearchRepository eCommandeSearchRepository;

    public ECommandeResource(ECommandeRepository eCommandeRepository, ECommandeSearchRepository eCommandeSearchRepository) {
        this.eCommandeRepository = eCommandeRepository;
        this.eCommandeSearchRepository = eCommandeSearchRepository;
    }

    /**
     * POST  /e-commandes : Create a new eCommande.
     *
     * @param eCommande the eCommande to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eCommande, or with status 400 (Bad Request) if the eCommande has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/e-commandes")
    @Timed
    public ResponseEntity<ECommande> createECommande(@RequestBody ECommande eCommande) throws URISyntaxException {
        log.debug("REST request to save ECommande : {}", eCommande);
        if (eCommande.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eCommande cannot already have an ID")).body(null);
        }
        ECommande result = eCommandeRepository.save(eCommande);
        eCommandeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/e-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /e-commandes : Updates an existing eCommande.
     *
     * @param eCommande the eCommande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eCommande,
     * or with status 400 (Bad Request) if the eCommande is not valid,
     * or with status 500 (Internal Server Error) if the eCommande couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/e-commandes")
    @Timed
    public ResponseEntity<ECommande> updateECommande(@RequestBody ECommande eCommande) throws URISyntaxException {
        log.debug("REST request to update ECommande : {}", eCommande);
        if (eCommande.getId() == null) {
            return createECommande(eCommande);
        }
        ECommande result = eCommandeRepository.save(eCommande);
        eCommandeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eCommande.getId().toString()))
            .body(result);
    }

    /**
     * GET  /e-commandes : get all the eCommandes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eCommandes in body
     */
    @GetMapping("/e-commandes")
    @Timed
    public List<ECommande> getAllECommandes() {
        log.debug("REST request to get all ECommandes");
        List<ECommande> eCommandes = eCommandeRepository.findAll();
        return eCommandes;
    }

    /**
     * GET  /e-commandes/:id : get the "id" eCommande.
     *
     * @param id the id of the eCommande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eCommande, or with status 404 (Not Found)
     */
    @GetMapping("/e-commandes/{id}")
    @Timed
    public ResponseEntity<ECommande> getECommande(@PathVariable Long id) {
        log.debug("REST request to get ECommande : {}", id);
        ECommande eCommande = eCommandeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eCommande));
    }

    /**
     * DELETE  /e-commandes/:id : delete the "id" eCommande.
     *
     * @param id the id of the eCommande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/e-commandes/{id}")
    @Timed
    public ResponseEntity<Void> deleteECommande(@PathVariable Long id) {
        log.debug("REST request to delete ECommande : {}", id);
        eCommandeRepository.delete(id);
        eCommandeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/e-commandes?query=:query : search for the eCommande corresponding
     * to the query.
     *
     * @param query the query of the eCommande search 
     * @return the result of the search
     */
    @GetMapping("/_search/e-commandes")
    @Timed
    public List<ECommande> searchECommandes(@RequestParam String query) {
        log.debug("REST request to search ECommandes for query {}", query);
        return StreamSupport
            .stream(eCommandeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

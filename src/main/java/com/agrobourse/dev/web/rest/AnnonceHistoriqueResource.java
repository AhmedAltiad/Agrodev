package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.AnnonceHistorique;

import com.agrobourse.dev.repository.AnnonceHistoriqueRepository;
import com.agrobourse.dev.repository.search.AnnonceHistoriqueSearchRepository;
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
 * REST controller for managing AnnonceHistorique.
 */
@RestController
@RequestMapping("/api")
public class AnnonceHistoriqueResource {

    private final Logger log = LoggerFactory.getLogger(AnnonceHistoriqueResource.class);

    private static final String ENTITY_NAME = "annonceHistorique";
        
    private final AnnonceHistoriqueRepository annonceHistoriqueRepository;

    private final AnnonceHistoriqueSearchRepository annonceHistoriqueSearchRepository;

    public AnnonceHistoriqueResource(AnnonceHistoriqueRepository annonceHistoriqueRepository, AnnonceHistoriqueSearchRepository annonceHistoriqueSearchRepository) {
        this.annonceHistoriqueRepository = annonceHistoriqueRepository;
        this.annonceHistoriqueSearchRepository = annonceHistoriqueSearchRepository;
    }

    /**
     * POST  /annonce-historiques : Create a new annonceHistorique.
     *
     * @param annonceHistorique the annonceHistorique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annonceHistorique, or with status 400 (Bad Request) if the annonceHistorique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annonce-historiques")
    @Timed
    public ResponseEntity<AnnonceHistorique> createAnnonceHistorique(@RequestBody AnnonceHistorique annonceHistorique) throws URISyntaxException {
        log.debug("REST request to save AnnonceHistorique : {}", annonceHistorique);
        if (annonceHistorique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new annonceHistorique cannot already have an ID")).body(null);
        }
        AnnonceHistorique result = annonceHistoriqueRepository.save(annonceHistorique);
        annonceHistoriqueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/annonce-historiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annonce-historiques : Updates an existing annonceHistorique.
     *
     * @param annonceHistorique the annonceHistorique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annonceHistorique,
     * or with status 400 (Bad Request) if the annonceHistorique is not valid,
     * or with status 500 (Internal Server Error) if the annonceHistorique couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annonce-historiques")
    @Timed
    public ResponseEntity<AnnonceHistorique> updateAnnonceHistorique(@RequestBody AnnonceHistorique annonceHistorique) throws URISyntaxException {
        log.debug("REST request to update AnnonceHistorique : {}", annonceHistorique);
        if (annonceHistorique.getId() == null) {
            return createAnnonceHistorique(annonceHistorique);
        }
        AnnonceHistorique result = annonceHistoriqueRepository.save(annonceHistorique);
        annonceHistoriqueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annonceHistorique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annonce-historiques : get all the annonceHistoriques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of annonceHistoriques in body
     */
    @GetMapping("/annonce-historiques")
    @Timed
    public List<AnnonceHistorique> getAllAnnonceHistoriques() {
        log.debug("REST request to get all AnnonceHistoriques");
        List<AnnonceHistorique> annonceHistoriques = annonceHistoriqueRepository.findAll();
        return annonceHistoriques;
    }

    /**
     * GET  /annonce-historiques/:id : get the "id" annonceHistorique.
     *
     * @param id the id of the annonceHistorique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annonceHistorique, or with status 404 (Not Found)
     */
    @GetMapping("/annonce-historiques/{id}")
    @Timed
    public ResponseEntity<AnnonceHistorique> getAnnonceHistorique(@PathVariable Long id) {
        log.debug("REST request to get AnnonceHistorique : {}", id);
        AnnonceHistorique annonceHistorique = annonceHistoriqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annonceHistorique));
    }

    /**
     * DELETE  /annonce-historiques/:id : delete the "id" annonceHistorique.
     *
     * @param id the id of the annonceHistorique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annonce-historiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnnonceHistorique(@PathVariable Long id) {
        log.debug("REST request to delete AnnonceHistorique : {}", id);
        annonceHistoriqueRepository.delete(id);
        annonceHistoriqueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/annonce-historiques?query=:query : search for the annonceHistorique corresponding
     * to the query.
     *
     * @param query the query of the annonceHistorique search 
     * @return the result of the search
     */
    @GetMapping("/_search/annonce-historiques")
    @Timed
    public List<AnnonceHistorique> searchAnnonceHistoriques(@RequestParam String query) {
        log.debug("REST request to search AnnonceHistoriques for query {}", query);
        return StreamSupport
            .stream(annonceHistoriqueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Agriculteur;

import com.agrobourse.dev.repository.AgriculteurRepository;
import com.agrobourse.dev.repository.search.AgriculteurSearchRepository;
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
 * REST controller for managing Agriculteur.
 */
@RestController
@RequestMapping("/api")
public class AgriculteurResource {

    private final Logger log = LoggerFactory.getLogger(AgriculteurResource.class);

    private static final String ENTITY_NAME = "agriculteur";
        
    private final AgriculteurRepository agriculteurRepository;

    private final AgriculteurSearchRepository agriculteurSearchRepository;

    public AgriculteurResource(AgriculteurRepository agriculteurRepository, AgriculteurSearchRepository agriculteurSearchRepository) {
        this.agriculteurRepository = agriculteurRepository;
        this.agriculteurSearchRepository = agriculteurSearchRepository;
    }

    /**
     * POST  /agriculteurs : Create a new agriculteur.
     *
     * @param agriculteur the agriculteur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agriculteur, or with status 400 (Bad Request) if the agriculteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agriculteurs")
    @Timed
    public ResponseEntity<Agriculteur> createAgriculteur(@RequestBody Agriculteur agriculteur) throws URISyntaxException {
        log.debug("REST request to save Agriculteur : {}", agriculteur);
        if (agriculteur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new agriculteur cannot already have an ID")).body(null);
        }
        Agriculteur result = agriculteurRepository.save(agriculteur);
        agriculteurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/agriculteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agriculteurs : Updates an existing agriculteur.
     *
     * @param agriculteur the agriculteur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agriculteur,
     * or with status 400 (Bad Request) if the agriculteur is not valid,
     * or with status 500 (Internal Server Error) if the agriculteur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agriculteurs")
    @Timed
    public ResponseEntity<Agriculteur> updateAgriculteur(@RequestBody Agriculteur agriculteur) throws URISyntaxException {
        log.debug("REST request to update Agriculteur : {}", agriculteur);
        if (agriculteur.getId() == null) {
            return createAgriculteur(agriculteur);
        }
        Agriculteur result = agriculteurRepository.save(agriculteur);
        agriculteurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agriculteur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agriculteurs : get all the agriculteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agriculteurs in body
     */
    @GetMapping("/agriculteurs")
    @Timed
    public List<Agriculteur> getAllAgriculteurs() {
        log.debug("REST request to get all Agriculteurs");
        List<Agriculteur> agriculteurs = agriculteurRepository.findAll();
        return agriculteurs;
    }

    /**
     * GET  /agriculteurs/:id : get the "id" agriculteur.
     *
     * @param id the id of the agriculteur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agriculteur, or with status 404 (Not Found)
     */
    @GetMapping("/agriculteurs/{id}")
    @Timed
    public ResponseEntity<Agriculteur> getAgriculteur(@PathVariable Long id) {
        log.debug("REST request to get Agriculteur : {}", id);
        Agriculteur agriculteur = agriculteurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agriculteur));
    }

    /**
     * DELETE  /agriculteurs/:id : delete the "id" agriculteur.
     *
     * @param id the id of the agriculteur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agriculteurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgriculteur(@PathVariable Long id) {
        log.debug("REST request to delete Agriculteur : {}", id);
        agriculteurRepository.delete(id);
        agriculteurSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/agriculteurs?query=:query : search for the agriculteur corresponding
     * to the query.
     *
     * @param query the query of the agriculteur search 
     * @return the result of the search
     */
    @GetMapping("/_search/agriculteurs")
    @Timed
    public List<Agriculteur> searchAgriculteurs(@RequestParam String query) {
        log.debug("REST request to search Agriculteurs for query {}", query);
        return StreamSupport
            .stream(agriculteurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

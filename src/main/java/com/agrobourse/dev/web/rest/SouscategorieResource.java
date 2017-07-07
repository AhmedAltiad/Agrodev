package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Souscategorie;

import com.agrobourse.dev.repository.SouscategorieRepository;
import com.agrobourse.dev.repository.search.SouscategorieSearchRepository;
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
 * REST controller for managing Souscategorie.
 */
@RestController
@RequestMapping("/api")
public class SouscategorieResource {

    private final Logger log = LoggerFactory.getLogger(SouscategorieResource.class);

    private static final String ENTITY_NAME = "souscategorie";
        
    private final SouscategorieRepository souscategorieRepository;

    private final SouscategorieSearchRepository souscategorieSearchRepository;

    public SouscategorieResource(SouscategorieRepository souscategorieRepository, SouscategorieSearchRepository souscategorieSearchRepository) {
        this.souscategorieRepository = souscategorieRepository;
        this.souscategorieSearchRepository = souscategorieSearchRepository;
    }

    /**
     * POST  /souscategories : Create a new souscategorie.
     *
     * @param souscategorie the souscategorie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new souscategorie, or with status 400 (Bad Request) if the souscategorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/souscategories")
    @Timed
    public ResponseEntity<Souscategorie> createSouscategorie(@RequestBody Souscategorie souscategorie) throws URISyntaxException {
        log.debug("REST request to save Souscategorie : {}", souscategorie);
        if (souscategorie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new souscategorie cannot already have an ID")).body(null);
        }
        Souscategorie result = souscategorieRepository.save(souscategorie);
        souscategorieSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/souscategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /souscategories : Updates an existing souscategorie.
     *
     * @param souscategorie the souscategorie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated souscategorie,
     * or with status 400 (Bad Request) if the souscategorie is not valid,
     * or with status 500 (Internal Server Error) if the souscategorie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/souscategories")
    @Timed
    public ResponseEntity<Souscategorie> updateSouscategorie(@RequestBody Souscategorie souscategorie) throws URISyntaxException {
        log.debug("REST request to update Souscategorie : {}", souscategorie);
        if (souscategorie.getId() == null) {
            return createSouscategorie(souscategorie);
        }
        Souscategorie result = souscategorieRepository.save(souscategorie);
        souscategorieSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, souscategorie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /souscategories : get all the souscategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of souscategories in body
     */
    @GetMapping("/souscategories")
    @Timed
    public List<Souscategorie> getAllSouscategories() {
        log.debug("REST request to get all Souscategories");
        List<Souscategorie> souscategories = souscategorieRepository.findAll();
        return souscategories;
    }

    /**
     * GET  /souscategories/:id : get the "id" souscategorie.
     *
     * @param id the id of the souscategorie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the souscategorie, or with status 404 (Not Found)
     */
    @GetMapping("/souscategories/{id}")
    @Timed
    public ResponseEntity<Souscategorie> getSouscategorie(@PathVariable Long id) {
        log.debug("REST request to get Souscategorie : {}", id);
        Souscategorie souscategorie = souscategorieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(souscategorie));
    }

    /**
     * DELETE  /souscategories/:id : delete the "id" souscategorie.
     *
     * @param id the id of the souscategorie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/souscategories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSouscategorie(@PathVariable Long id) {
        log.debug("REST request to delete Souscategorie : {}", id);
        souscategorieRepository.delete(id);
        souscategorieSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/souscategories?query=:query : search for the souscategorie corresponding
     * to the query.
     *
     * @param query the query of the souscategorie search 
     * @return the result of the search
     */
    @GetMapping("/_search/souscategories")
    @Timed
    public List<Souscategorie> searchSouscategories(@RequestParam String query) {
        log.debug("REST request to search Souscategories for query {}", query);
        return StreamSupport
            .stream(souscategorieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

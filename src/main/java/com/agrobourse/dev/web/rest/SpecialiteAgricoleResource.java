package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.SpecialiteAgricole;

import com.agrobourse.dev.repository.SpecialiteAgricoleRepository;
import com.agrobourse.dev.repository.search.SpecialiteAgricoleSearchRepository;
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
 * REST controller for managing SpecialiteAgricole.
 */
@RestController
@RequestMapping("/api")
public class SpecialiteAgricoleResource {

    private final Logger log = LoggerFactory.getLogger(SpecialiteAgricoleResource.class);

    private static final String ENTITY_NAME = "specialiteAgricole";
        
    private final SpecialiteAgricoleRepository specialiteAgricoleRepository;

    private final SpecialiteAgricoleSearchRepository specialiteAgricoleSearchRepository;

    public SpecialiteAgricoleResource(SpecialiteAgricoleRepository specialiteAgricoleRepository, SpecialiteAgricoleSearchRepository specialiteAgricoleSearchRepository) {
        this.specialiteAgricoleRepository = specialiteAgricoleRepository;
        this.specialiteAgricoleSearchRepository = specialiteAgricoleSearchRepository;
    }

    /**
     * POST  /specialite-agricoles : Create a new specialiteAgricole.
     *
     * @param specialiteAgricole the specialiteAgricole to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialiteAgricole, or with status 400 (Bad Request) if the specialiteAgricole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/specialite-agricoles")
    @Timed
    public ResponseEntity<SpecialiteAgricole> createSpecialiteAgricole(@RequestBody SpecialiteAgricole specialiteAgricole) throws URISyntaxException {
        log.debug("REST request to save SpecialiteAgricole : {}", specialiteAgricole);
        if (specialiteAgricole.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new specialiteAgricole cannot already have an ID")).body(null);
        }
        SpecialiteAgricole result = specialiteAgricoleRepository.save(specialiteAgricole);
        specialiteAgricoleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/specialite-agricoles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /specialite-agricoles : Updates an existing specialiteAgricole.
     *
     * @param specialiteAgricole the specialiteAgricole to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialiteAgricole,
     * or with status 400 (Bad Request) if the specialiteAgricole is not valid,
     * or with status 500 (Internal Server Error) if the specialiteAgricole couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/specialite-agricoles")
    @Timed
    public ResponseEntity<SpecialiteAgricole> updateSpecialiteAgricole(@RequestBody SpecialiteAgricole specialiteAgricole) throws URISyntaxException {
        log.debug("REST request to update SpecialiteAgricole : {}", specialiteAgricole);
        if (specialiteAgricole.getId() == null) {
            return createSpecialiteAgricole(specialiteAgricole);
        }
        SpecialiteAgricole result = specialiteAgricoleRepository.save(specialiteAgricole);
        specialiteAgricoleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialiteAgricole.getId().toString()))
            .body(result);
    }

    /**
     * GET  /specialite-agricoles : get all the specialiteAgricoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialiteAgricoles in body
     */
    @GetMapping("/specialite-agricoles")
    @Timed
    public List<SpecialiteAgricole> getAllSpecialiteAgricoles() {
        log.debug("REST request to get all SpecialiteAgricoles");
        List<SpecialiteAgricole> specialiteAgricoles = specialiteAgricoleRepository.findAll();
        return specialiteAgricoles;
    }

    /**
     * GET  /specialite-agricoles/:id : get the "id" specialiteAgricole.
     *
     * @param id the id of the specialiteAgricole to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialiteAgricole, or with status 404 (Not Found)
     */
    @GetMapping("/specialite-agricoles/{id}")
    @Timed
    public ResponseEntity<SpecialiteAgricole> getSpecialiteAgricole(@PathVariable Long id) {
        log.debug("REST request to get SpecialiteAgricole : {}", id);
        SpecialiteAgricole specialiteAgricole = specialiteAgricoleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(specialiteAgricole));
    }

    /**
     * DELETE  /specialite-agricoles/:id : delete the "id" specialiteAgricole.
     *
     * @param id the id of the specialiteAgricole to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/specialite-agricoles/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpecialiteAgricole(@PathVariable Long id) {
        log.debug("REST request to delete SpecialiteAgricole : {}", id);
        specialiteAgricoleRepository.delete(id);
        specialiteAgricoleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/specialite-agricoles?query=:query : search for the specialiteAgricole corresponding
     * to the query.
     *
     * @param query the query of the specialiteAgricole search 
     * @return the result of the search
     */
    @GetMapping("/_search/specialite-agricoles")
    @Timed
    public List<SpecialiteAgricole> searchSpecialiteAgricoles(@RequestParam String query) {
        log.debug("REST request to search SpecialiteAgricoles for query {}", query);
        return StreamSupport
            .stream(specialiteAgricoleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

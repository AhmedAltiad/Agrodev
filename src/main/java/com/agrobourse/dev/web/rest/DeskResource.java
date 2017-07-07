package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Desk;

import com.agrobourse.dev.repository.DeskRepository;
import com.agrobourse.dev.repository.search.DeskSearchRepository;
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
 * REST controller for managing Desk.
 */
@RestController
@RequestMapping("/api")
public class DeskResource {

    private final Logger log = LoggerFactory.getLogger(DeskResource.class);

    private static final String ENTITY_NAME = "desk";
        
    private final DeskRepository deskRepository;

    private final DeskSearchRepository deskSearchRepository;

    public DeskResource(DeskRepository deskRepository, DeskSearchRepository deskSearchRepository) {
        this.deskRepository = deskRepository;
        this.deskSearchRepository = deskSearchRepository;
    }

    /**
     * POST  /desks : Create a new desk.
     *
     * @param desk the desk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new desk, or with status 400 (Bad Request) if the desk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/desks")
    @Timed
    public ResponseEntity<Desk> createDesk(@RequestBody Desk desk) throws URISyntaxException {
        log.debug("REST request to save Desk : {}", desk);
        if (desk.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new desk cannot already have an ID")).body(null);
        }
        Desk result = deskRepository.save(desk);
        deskSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/desks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /desks : Updates an existing desk.
     *
     * @param desk the desk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated desk,
     * or with status 400 (Bad Request) if the desk is not valid,
     * or with status 500 (Internal Server Error) if the desk couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/desks")
    @Timed
    public ResponseEntity<Desk> updateDesk(@RequestBody Desk desk) throws URISyntaxException {
        log.debug("REST request to update Desk : {}", desk);
        if (desk.getId() == null) {
            return createDesk(desk);
        }
        Desk result = deskRepository.save(desk);
        deskSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, desk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /desks : get all the desks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of desks in body
     */
    @GetMapping("/desks")
    @Timed
    public List<Desk> getAllDesks() {
        log.debug("REST request to get all Desks");
        List<Desk> desks = deskRepository.findAll();
        return desks;
    }

    /**
     * GET  /desks/:id : get the "id" desk.
     *
     * @param id the id of the desk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the desk, or with status 404 (Not Found)
     */
    @GetMapping("/desks/{id}")
    @Timed
    public ResponseEntity<Desk> getDesk(@PathVariable Long id) {
        log.debug("REST request to get Desk : {}", id);
        Desk desk = deskRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(desk));
    }

    /**
     * DELETE  /desks/:id : delete the "id" desk.
     *
     * @param id the id of the desk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/desks/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesk(@PathVariable Long id) {
        log.debug("REST request to delete Desk : {}", id);
        deskRepository.delete(id);
        deskSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/desks?query=:query : search for the desk corresponding
     * to the query.
     *
     * @param query the query of the desk search 
     * @return the result of the search
     */
    @GetMapping("/_search/desks")
    @Timed
    public List<Desk> searchDesks(@RequestParam String query) {
        log.debug("REST request to search Desks for query {}", query);
        return StreamSupport
            .stream(deskSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

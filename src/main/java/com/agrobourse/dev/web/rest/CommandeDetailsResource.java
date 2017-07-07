package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.CommandeDetails;

import com.agrobourse.dev.repository.CommandeDetailsRepository;
import com.agrobourse.dev.repository.search.CommandeDetailsSearchRepository;
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
 * REST controller for managing CommandeDetails.
 */
@RestController
@RequestMapping("/api")
public class CommandeDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CommandeDetailsResource.class);

    private static final String ENTITY_NAME = "commandeDetails";
        
    private final CommandeDetailsRepository commandeDetailsRepository;

    private final CommandeDetailsSearchRepository commandeDetailsSearchRepository;

    public CommandeDetailsResource(CommandeDetailsRepository commandeDetailsRepository, CommandeDetailsSearchRepository commandeDetailsSearchRepository) {
        this.commandeDetailsRepository = commandeDetailsRepository;
        this.commandeDetailsSearchRepository = commandeDetailsSearchRepository;
    }

    /**
     * POST  /commande-details : Create a new commandeDetails.
     *
     * @param commandeDetails the commandeDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commandeDetails, or with status 400 (Bad Request) if the commandeDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commande-details")
    @Timed
    public ResponseEntity<CommandeDetails> createCommandeDetails(@RequestBody CommandeDetails commandeDetails) throws URISyntaxException {
        log.debug("REST request to save CommandeDetails : {}", commandeDetails);
        if (commandeDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commandeDetails cannot already have an ID")).body(null);
        }
        CommandeDetails result = commandeDetailsRepository.save(commandeDetails);
        commandeDetailsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/commande-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commande-details : Updates an existing commandeDetails.
     *
     * @param commandeDetails the commandeDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandeDetails,
     * or with status 400 (Bad Request) if the commandeDetails is not valid,
     * or with status 500 (Internal Server Error) if the commandeDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commande-details")
    @Timed
    public ResponseEntity<CommandeDetails> updateCommandeDetails(@RequestBody CommandeDetails commandeDetails) throws URISyntaxException {
        log.debug("REST request to update CommandeDetails : {}", commandeDetails);
        if (commandeDetails.getId() == null) {
            return createCommandeDetails(commandeDetails);
        }
        CommandeDetails result = commandeDetailsRepository.save(commandeDetails);
        commandeDetailsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commandeDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commande-details : get all the commandeDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commandeDetails in body
     */
    @GetMapping("/commande-details")
    @Timed
    public List<CommandeDetails> getAllCommandeDetails() {
        log.debug("REST request to get all CommandeDetails");
        List<CommandeDetails> commandeDetails = commandeDetailsRepository.findAll();
        return commandeDetails;
    }

    /**
     * GET  /commande-details/:id : get the "id" commandeDetails.
     *
     * @param id the id of the commandeDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandeDetails, or with status 404 (Not Found)
     */
    @GetMapping("/commande-details/{id}")
    @Timed
    public ResponseEntity<CommandeDetails> getCommandeDetails(@PathVariable Long id) {
        log.debug("REST request to get CommandeDetails : {}", id);
        CommandeDetails commandeDetails = commandeDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commandeDetails));
    }

    /**
     * DELETE  /commande-details/:id : delete the "id" commandeDetails.
     *
     * @param id the id of the commandeDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commande-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommandeDetails(@PathVariable Long id) {
        log.debug("REST request to delete CommandeDetails : {}", id);
        commandeDetailsRepository.delete(id);
        commandeDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commande-details?query=:query : search for the commandeDetails corresponding
     * to the query.
     *
     * @param query the query of the commandeDetails search 
     * @return the result of the search
     */
    @GetMapping("/_search/commande-details")
    @Timed
    public List<CommandeDetails> searchCommandeDetails(@RequestParam String query) {
        log.debug("REST request to search CommandeDetails for query {}", query);
        return StreamSupport
            .stream(commandeDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

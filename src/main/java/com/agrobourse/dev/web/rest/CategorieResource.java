package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Categorie;

import com.agrobourse.dev.repository.CategorieRepository;
import com.agrobourse.dev.repository.search.CategorieSearchRepository;
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
 * REST controller for managing Categorie.
 */
@RestController
@RequestMapping("/api")
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

    private static final String ENTITY_NAME = "categorie";
        
    private final CategorieRepository categorieRepository;

    private final CategorieSearchRepository categorieSearchRepository;

    public CategorieResource(CategorieRepository categorieRepository, CategorieSearchRepository categorieSearchRepository) {
        this.categorieRepository = categorieRepository;
        this.categorieSearchRepository = categorieSearchRepository;
    }

    /**
     * POST  /categories : Create a new categorie.
     *
     * @param categorie the categorie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorie, or with status 400 (Bad Request) if the categorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categories")
    @Timed
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorie);
        if (categorie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new categorie cannot already have an ID")).body(null);
        }
        Categorie result = categorieRepository.save(categorie);
        categorieSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categories : Updates an existing categorie.
     *
     * @param categorie the categorie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categorie,
     * or with status 400 (Bad Request) if the categorie is not valid,
     * or with status 500 (Internal Server Error) if the categorie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categories")
    @Timed
    public ResponseEntity<Categorie> updateCategorie(@RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorie);
        if (categorie.getId() == null) {
            return createCategorie(categorie);
        }
        Categorie result = categorieRepository.save(categorie);
        categorieSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categorie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     */
    @GetMapping("/categories")
    @Timed
    public List<Categorie> getAllCategories() {
        log.debug("REST request to get all Categories");
        List<Categorie> categories = categorieRepository.findAll();
        return categories;
    }

    /**
     * GET  /categories/:id : get the "id" categorie.
     *
     * @param id the id of the categorie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categorie, or with status 404 (Not Found)
     */
    @GetMapping("/categories/{id}")
    @Timed
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        Categorie categorie = categorieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categorie));
    }

    /**
     * DELETE  /categories/:id : delete the "id" categorie.
     *
     * @param id the id of the categorie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        log.debug("REST request to delete Categorie : {}", id);
        categorieRepository.delete(id);
        categorieSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/categories?query=:query : search for the categorie corresponding
     * to the query.
     *
     * @param query the query of the categorie search 
     * @return the result of the search
     */
    @GetMapping("/_search/categories")
    @Timed
    public List<Categorie> searchCategories(@RequestParam String query) {
        log.debug("REST request to search Categories for query {}", query);
        return StreamSupport
            .stream(categorieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

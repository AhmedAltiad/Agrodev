package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.CvSpecialities;

import com.agrobourse.dev.repository.CvSpecialitiesRepository;
import com.agrobourse.dev.repository.search.CvSpecialitiesSearchRepository;
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
 * REST controller for managing CvSpecialities.
 */
@RestController
@RequestMapping("/api")
public class CvSpecialitiesResource {

    private final Logger log = LoggerFactory.getLogger(CvSpecialitiesResource.class);

    private static final String ENTITY_NAME = "cvSpecialities";
        
    private final CvSpecialitiesRepository cvSpecialitiesRepository;

    private final CvSpecialitiesSearchRepository cvSpecialitiesSearchRepository;

    public CvSpecialitiesResource(CvSpecialitiesRepository cvSpecialitiesRepository, CvSpecialitiesSearchRepository cvSpecialitiesSearchRepository) {
        this.cvSpecialitiesRepository = cvSpecialitiesRepository;
        this.cvSpecialitiesSearchRepository = cvSpecialitiesSearchRepository;
    }

    /**
     * POST  /cv-specialities : Create a new cvSpecialities.
     *
     * @param cvSpecialities the cvSpecialities to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cvSpecialities, or with status 400 (Bad Request) if the cvSpecialities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cv-specialities")
    @Timed
    public ResponseEntity<CvSpecialities> createCvSpecialities(@RequestBody CvSpecialities cvSpecialities) throws URISyntaxException {
        log.debug("REST request to save CvSpecialities : {}", cvSpecialities);
        if (cvSpecialities.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cvSpecialities cannot already have an ID")).body(null);
        }
        CvSpecialities result = cvSpecialitiesRepository.save(cvSpecialities);
        cvSpecialitiesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cv-specialities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cv-specialities : Updates an existing cvSpecialities.
     *
     * @param cvSpecialities the cvSpecialities to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cvSpecialities,
     * or with status 400 (Bad Request) if the cvSpecialities is not valid,
     * or with status 500 (Internal Server Error) if the cvSpecialities couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cv-specialities")
    @Timed
    public ResponseEntity<CvSpecialities> updateCvSpecialities(@RequestBody CvSpecialities cvSpecialities) throws URISyntaxException {
        log.debug("REST request to update CvSpecialities : {}", cvSpecialities);
        if (cvSpecialities.getId() == null) {
            return createCvSpecialities(cvSpecialities);
        }
        CvSpecialities result = cvSpecialitiesRepository.save(cvSpecialities);
        cvSpecialitiesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cvSpecialities.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cv-specialities : get all the cvSpecialities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cvSpecialities in body
     */
    @GetMapping("/cv-specialities")
    @Timed
    public List<CvSpecialities> getAllCvSpecialities() {
        log.debug("REST request to get all CvSpecialities");
        List<CvSpecialities> cvSpecialities = cvSpecialitiesRepository.findAll();
        return cvSpecialities;
    }

    /**
     * GET  /cv-specialities/:id : get the "id" cvSpecialities.
     *
     * @param id the id of the cvSpecialities to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cvSpecialities, or with status 404 (Not Found)
     */
    @GetMapping("/cv-specialities/{id}")
    @Timed
    public ResponseEntity<CvSpecialities> getCvSpecialities(@PathVariable Long id) {
        log.debug("REST request to get CvSpecialities : {}", id);
        CvSpecialities cvSpecialities = cvSpecialitiesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cvSpecialities));
    }

    /**
     * DELETE  /cv-specialities/:id : delete the "id" cvSpecialities.
     *
     * @param id the id of the cvSpecialities to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cv-specialities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCvSpecialities(@PathVariable Long id) {
        log.debug("REST request to delete CvSpecialities : {}", id);
        cvSpecialitiesRepository.delete(id);
        cvSpecialitiesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cv-specialities?query=:query : search for the cvSpecialities corresponding
     * to the query.
     *
     * @param query the query of the cvSpecialities search 
     * @return the result of the search
     */
    @GetMapping("/_search/cv-specialities")
    @Timed
    public List<CvSpecialities> searchCvSpecialities(@RequestParam String query) {
        log.debug("REST request to search CvSpecialities for query {}", query);
        return StreamSupport
            .stream(cvSpecialitiesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.Annonce;
import com.agrobourse.dev.domain.AnnonceImage;
import com.agrobourse.dev.repository.AnnonceRepository;
import com.agrobourse.dev.repository.search.AnnonceSearchRepository;
import com.agrobourse.dev.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileOutputStream;  
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Annonce.
 */
@RestController
@RequestMapping("/api")
public class AnnonceResource {

    private final Logger log = LoggerFactory.getLogger(AnnonceResource.class);

    private static final String ENTITY_NAME = "annonce";
        
    private final AnnonceRepository annonceRepository;

    private final AnnonceSearchRepository annonceSearchRepository;

    public AnnonceResource(AnnonceRepository annonceRepository, AnnonceSearchRepository annonceSearchRepository) {
        this.annonceRepository = annonceRepository;
        this.annonceSearchRepository = annonceSearchRepository;
    }

    /**
     * POST  /annonces : Create a new annonce.
     *
     * @param annonce the annonce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annonce, or with status 400 (Bad Request) if the annonce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @Autowired
   private HttpServletRequest request;
    @PostMapping("/annonces")
    @Timed
    public ResponseEntity<Annonce> createAnnonce(@Valid @RequestBody AnnonceImage annonceImage) throws URISyntaxException {
        log.debug("REST request to save Annonce : {}", annonceImage);
        Annonce annonce = new Annonce();
        if (annonceImage.getFile() == null) {
          System.out.println("choisir une image");
        }else{

System.out.println("1111111111111111111111111");
        String uploadsDir = "/images/";
                String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
          System.out.println("22222222222222222222");      
                   if(! new File(realPathtoUploads).exists()){
                       new File(realPathtoUploads).mkdir();
                   }
    String orgName = Math.round(Math.random() * 100000)+""+new SimpleDateFormat("yyyyMMddHHmmssSSSSSS").format(new Date())
     +annonceImage.getType().replace('/','.');
                   String filePath = realPathtoUploads + "/" + orgName;
                   File dest = new File(filePath);
                   annonce.setImage("/images/"+orgName);
                   try{
                   new FileOutputStream(dest).write(annonceImage.getFile());
                   }
                   catch (Exception e){System.out.println(e);
                   }
           }
                Annonce annoncewithoutimage= annonceImage.getAnnonce();
                 annonce.setId(null);
                 annonce.setAnnoncebody(annoncewithoutimage.getAnnoncebody());
                 annonce.setDatedebut(annoncewithoutimage.getDatedebut()) ;
                 annonce.setDatefin(annoncewithoutimage.getDatefin());
                 annonce.setSujet(annoncewithoutimage.getSujet());

        
        Annonce result = annonceRepository.save(annonce);
        annonceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/annonces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annonces : Updates an existing annonce.
     *
     * @param annonce the annonce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annonce,
     * or with status 400 (Bad Request) if the annonce is not valid,
     * or with status 500 (Internal Server Error) if the annonce couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annonces")
    @Timed
    public ResponseEntity<Annonce> updateAnnonce(@Valid @RequestBody Annonce annonce) throws URISyntaxException {
        log.debug("REST request to update Annonce : {}", annonce);
        if (annonce.getId() == null) {
            AnnonceImage annonceImage=new AnnonceImage();
        
            return createAnnonce(annonceImage);
        }
        Annonce result = annonceRepository.save(annonce);
        annonceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annonce.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annonces : get all the annonces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of annonces in body
     */
    @GetMapping("/annonces")
    @Timed
    public List<Annonce> getAllAnnonces() {
        log.debug("REST request to get all Annonces");
        List<Annonce> annonces = annonceRepository.findAll();
        return annonces;
    }

    /**
     * GET  /annonces/:id : get the "id" annonce.
     *
     * @param id the id of the annonce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annonce, or with status 404 (Not Found)
     */
    @GetMapping("/annonces/{id}")
    @Timed
    public ResponseEntity<Annonce> getAnnonce(@PathVariable Long id) {
        log.debug("REST request to get Annonce : {}", id);
        Annonce annonce = annonceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annonce));
    }

    /**
     * DELETE  /annonces/:id : delete the "id" annonce.
     *
     * @param id the id of the annonce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annonces/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        log.debug("REST request to delete Annonce : {}", id);
        annonceRepository.delete(id);
        annonceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/annonces?query=:query : search for the annonce corresponding
     * to the query.
     *
     * @param query the query of the annonce search 
     * @return the result of the search
     */
    @GetMapping("/_search/annonces")
    @Timed
    public List<Annonce> searchAnnonces(@RequestParam String query) {
        log.debug("REST request to search Annonces for query {}", query);
        return StreamSupport
            .stream(annonceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}

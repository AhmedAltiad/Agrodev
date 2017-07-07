package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Annonce;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Annonce entity.
 */
@SuppressWarnings("unused")
public interface AnnonceRepository extends JpaRepository<Annonce,Long> {

    @Query("select distinct annonce from Annonce annonce left join fetch annonce.likedBies")
    List<Annonce> findAllWithEagerRelationships();

    @Query("select annonce from Annonce annonce left join fetch annonce.likedBies where annonce.id =:id")
    Annonce findOneWithEagerRelationships(@Param("id") Long id);

}

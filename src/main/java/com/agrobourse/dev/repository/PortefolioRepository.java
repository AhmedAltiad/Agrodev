package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Portefolio;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Portefolio entity.
 */
@SuppressWarnings("unused")
public interface PortefolioRepository extends JpaRepository<Portefolio,Long> {

    @Query("select distinct portefolio from Portefolio portefolio left join fetch portefolio.varietes")
    List<Portefolio> findAllWithEagerRelationships();

    @Query("select portefolio from Portefolio portefolio left join fetch portefolio.varietes where portefolio.id =:id")
    Portefolio findOneWithEagerRelationships(@Param("id") Long id);

}

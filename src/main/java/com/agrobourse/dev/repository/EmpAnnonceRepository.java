package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.EmpAnnonce;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmpAnnonce entity.
 */
@SuppressWarnings("unused")
public interface EmpAnnonceRepository extends JpaRepository<EmpAnnonce,Long> {

    @Query("select distinct empAnnonce from EmpAnnonce empAnnonce left join fetch empAnnonce.postuls")
    List<EmpAnnonce> findAllWithEagerRelationships();

    @Query("select empAnnonce from EmpAnnonce empAnnonce left join fetch empAnnonce.postuls where empAnnonce.id =:id")
    EmpAnnonce findOneWithEagerRelationships(@Param("id") Long id);

}

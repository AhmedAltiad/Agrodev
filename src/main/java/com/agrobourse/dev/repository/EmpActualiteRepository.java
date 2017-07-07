package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.EmpActualite;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmpActualite entity.
 */
@SuppressWarnings("unused")
public interface EmpActualiteRepository extends JpaRepository<EmpActualite,Long> {

    @Query("select distinct empActualite from EmpActualite empActualite left join fetch empActualite.localites")
    List<EmpActualite> findAllWithEagerRelationships();

    @Query("select empActualite from EmpActualite empActualite left join fetch empActualite.localites where empActualite.id =:id")
    EmpActualite findOneWithEagerRelationships(@Param("id") Long id);

}

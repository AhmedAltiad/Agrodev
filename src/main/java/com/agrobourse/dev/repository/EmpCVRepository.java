package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.EmpCV;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmpCV entity.
 */
@SuppressWarnings("unused")
public interface EmpCVRepository extends JpaRepository<EmpCV,Long> {

    @Query("select distinct empCV from EmpCV empCV left join fetch empCV.cvSpecialities")
    List<EmpCV> findAllWithEagerRelationships();

    @Query("select empCV from EmpCV empCV left join fetch empCV.cvSpecialities where empCV.id =:id")
    EmpCV findOneWithEagerRelationships(@Param("id") Long id);

}

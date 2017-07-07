package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.CvSpecialities;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CvSpecialities entity.
 */
@SuppressWarnings("unused")
public interface CvSpecialitiesRepository extends JpaRepository<CvSpecialities,Long> {

}

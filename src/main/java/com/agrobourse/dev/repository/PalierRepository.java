package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Palier;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Palier entity.
 */
@SuppressWarnings("unused")
public interface PalierRepository extends JpaRepository<Palier,Long> {

}

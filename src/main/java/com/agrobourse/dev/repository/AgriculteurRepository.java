package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Agriculteur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Agriculteur entity.
 */
@SuppressWarnings("unused")
public interface AgriculteurRepository extends JpaRepository<Agriculteur,Long> {

}

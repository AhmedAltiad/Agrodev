package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Variete;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Variete entity.
 */
@SuppressWarnings("unused")
public interface VarieteRepository extends JpaRepository<Variete,Long> {

}

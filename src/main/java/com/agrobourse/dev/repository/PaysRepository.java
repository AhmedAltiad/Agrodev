package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Pays;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pays entity.
 */
@SuppressWarnings("unused")
public interface PaysRepository extends JpaRepository<Pays,Long> {

}

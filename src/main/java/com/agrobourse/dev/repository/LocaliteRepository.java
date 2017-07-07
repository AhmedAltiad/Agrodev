package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Localite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Localite entity.
 */
@SuppressWarnings("unused")
public interface LocaliteRepository extends JpaRepository<Localite,Long> {

}

package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Gouvernorat;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gouvernorat entity.
 */
@SuppressWarnings("unused")
public interface GouvernoratRepository extends JpaRepository<Gouvernorat,Long> {

}

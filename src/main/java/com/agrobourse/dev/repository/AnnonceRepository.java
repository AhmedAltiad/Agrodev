package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Annonce;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Annonce entity.
 */
@SuppressWarnings("unused")
public interface AnnonceRepository extends JpaRepository<Annonce,Long> {

}

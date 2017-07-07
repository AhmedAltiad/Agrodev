package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.AnnonceHistorique;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AnnonceHistorique entity.
 */
@SuppressWarnings("unused")
public interface AnnonceHistoriqueRepository extends JpaRepository<AnnonceHistorique,Long> {

}

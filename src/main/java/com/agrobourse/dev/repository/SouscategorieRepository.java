package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Souscategorie;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Souscategorie entity.
 */
@SuppressWarnings("unused")
public interface SouscategorieRepository extends JpaRepository<Souscategorie,Long> {

}

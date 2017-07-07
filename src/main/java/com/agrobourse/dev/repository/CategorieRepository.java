package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Categorie;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Categorie entity.
 */
@SuppressWarnings("unused")
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

}

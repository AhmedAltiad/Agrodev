package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Produit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Produit entity.
 */
@SuppressWarnings("unused")
public interface ProduitRepository extends JpaRepository<Produit,Long> {

}

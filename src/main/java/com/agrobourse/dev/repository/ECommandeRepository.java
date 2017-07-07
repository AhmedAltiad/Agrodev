package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.ECommande;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ECommande entity.
 */
@SuppressWarnings("unused")
public interface ECommandeRepository extends JpaRepository<ECommande,Long> {

}

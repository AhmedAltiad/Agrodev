package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.ECommandeHistorique;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ECommandeHistorique entity.
 */
@SuppressWarnings("unused")
public interface ECommandeHistoriqueRepository extends JpaRepository<ECommandeHistorique,Long> {

}

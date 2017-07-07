package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.TransactionHistorique;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransactionHistorique entity.
 */
@SuppressWarnings("unused")
public interface TransactionHistoriqueRepository extends JpaRepository<TransactionHistorique,Long> {

}

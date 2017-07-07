package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Production;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Production entity.
 */
@SuppressWarnings("unused")
public interface ProductionRepository extends JpaRepository<Production,Long> {

}

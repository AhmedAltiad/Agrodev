package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Trade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Trade entity.
 */
@SuppressWarnings("unused")
public interface TradeRepository extends JpaRepository<Trade,Long> {

}

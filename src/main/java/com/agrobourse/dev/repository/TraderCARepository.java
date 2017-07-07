package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.TraderCA;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraderCA entity.
 */
@SuppressWarnings("unused")
public interface TraderCARepository extends JpaRepository<TraderCA,Long> {

}

package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.ActiviteAgricole;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ActiviteAgricole entity.
 */
@SuppressWarnings("unused")
public interface ActiviteAgricoleRepository extends JpaRepository<ActiviteAgricole,Long> {

}

package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.SpecialiteAgricole;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SpecialiteAgricole entity.
 */
@SuppressWarnings("unused")
public interface SpecialiteAgricoleRepository extends JpaRepository<SpecialiteAgricole,Long> {

}

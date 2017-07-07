package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.View;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the View entity.
 */
@SuppressWarnings("unused")
public interface ViewRepository extends JpaRepository<View,Long> {

}

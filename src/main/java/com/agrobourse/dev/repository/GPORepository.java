package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.GPO;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GPO entity.
 */
@SuppressWarnings("unused")
public interface GPORepository extends JpaRepository<GPO,Long> {

}

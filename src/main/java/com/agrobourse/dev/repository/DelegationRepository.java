package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Delegation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Delegation entity.
 */
@SuppressWarnings("unused")
public interface DelegationRepository extends JpaRepository<Delegation,Long> {

}

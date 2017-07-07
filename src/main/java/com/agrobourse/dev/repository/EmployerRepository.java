package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Employer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employer entity.
 */
@SuppressWarnings("unused")
public interface EmployerRepository extends JpaRepository<Employer,Long> {

}

package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.CommandeDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommandeDetails entity.
 */
@SuppressWarnings("unused")
public interface CommandeDetailsRepository extends JpaRepository<CommandeDetails,Long> {

}

package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.AnnonceChangement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AnnonceChangement entity.
 */
@SuppressWarnings("unused")
public interface AnnonceChangementRepository extends JpaRepository<AnnonceChangement,Long> {

}

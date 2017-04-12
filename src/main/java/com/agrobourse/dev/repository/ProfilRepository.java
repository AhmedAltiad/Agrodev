package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.Profil;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Profil entity.
 */
@SuppressWarnings("unused")
public interface ProfilRepository extends JpaRepository<Profil,Long> {

}

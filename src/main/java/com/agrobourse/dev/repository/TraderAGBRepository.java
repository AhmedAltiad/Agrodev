package com.agrobourse.dev.repository;

import com.agrobourse.dev.domain.TraderAGB;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraderAGB entity.
 */
@SuppressWarnings("unused")
public interface TraderAGBRepository extends JpaRepository<TraderAGB,Long> {

}

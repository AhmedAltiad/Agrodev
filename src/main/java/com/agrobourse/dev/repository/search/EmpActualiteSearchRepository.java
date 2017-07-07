package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.EmpActualite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmpActualite entity.
 */
public interface EmpActualiteSearchRepository extends ElasticsearchRepository<EmpActualite, Long> {
}

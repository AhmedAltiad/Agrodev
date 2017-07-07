package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.EmpCV;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmpCV entity.
 */
public interface EmpCVSearchRepository extends ElasticsearchRepository<EmpCV, Long> {
}

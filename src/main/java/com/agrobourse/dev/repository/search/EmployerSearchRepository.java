package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Employer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Employer entity.
 */
public interface EmployerSearchRepository extends ElasticsearchRepository<Employer, Long> {
}

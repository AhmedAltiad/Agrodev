package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Agriculteur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Agriculteur entity.
 */
public interface AgriculteurSearchRepository extends ElasticsearchRepository<Agriculteur, Long> {
}

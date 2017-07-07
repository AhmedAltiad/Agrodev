package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Variete;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Variete entity.
 */
public interface VarieteSearchRepository extends ElasticsearchRepository<Variete, Long> {
}

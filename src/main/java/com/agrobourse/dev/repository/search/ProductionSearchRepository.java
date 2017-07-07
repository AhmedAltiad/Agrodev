package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Production;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Production entity.
 */
public interface ProductionSearchRepository extends ElasticsearchRepository<Production, Long> {
}

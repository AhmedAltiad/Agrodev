package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Palier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Palier entity.
 */
public interface PalierSearchRepository extends ElasticsearchRepository<Palier, Long> {
}

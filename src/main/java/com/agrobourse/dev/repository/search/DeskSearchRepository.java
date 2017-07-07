package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Desk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Desk entity.
 */
public interface DeskSearchRepository extends ElasticsearchRepository<Desk, Long> {
}

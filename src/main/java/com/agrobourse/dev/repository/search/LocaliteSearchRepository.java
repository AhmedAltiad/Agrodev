package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Localite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Localite entity.
 */
public interface LocaliteSearchRepository extends ElasticsearchRepository<Localite, Long> {
}

package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.View;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the View entity.
 */
public interface ViewSearchRepository extends ElasticsearchRepository<View, Long> {
}

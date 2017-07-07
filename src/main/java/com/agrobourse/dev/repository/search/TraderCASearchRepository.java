package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.TraderCA;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TraderCA entity.
 */
public interface TraderCASearchRepository extends ElasticsearchRepository<TraderCA, Long> {
}

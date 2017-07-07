package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.TraderAGB;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TraderAGB entity.
 */
public interface TraderAGBSearchRepository extends ElasticsearchRepository<TraderAGB, Long> {
}

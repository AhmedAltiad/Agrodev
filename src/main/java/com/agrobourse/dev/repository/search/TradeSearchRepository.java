package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Trade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Trade entity.
 */
public interface TradeSearchRepository extends ElasticsearchRepository<Trade, Long> {
}

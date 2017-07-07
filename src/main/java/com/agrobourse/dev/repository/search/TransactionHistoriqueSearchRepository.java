package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.TransactionHistorique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TransactionHistorique entity.
 */
public interface TransactionHistoriqueSearchRepository extends ElasticsearchRepository<TransactionHistorique, Long> {
}

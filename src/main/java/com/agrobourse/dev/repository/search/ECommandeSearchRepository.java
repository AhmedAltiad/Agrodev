package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.ECommande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ECommande entity.
 */
public interface ECommandeSearchRepository extends ElasticsearchRepository<ECommande, Long> {
}

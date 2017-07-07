package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.ECommandeHistorique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ECommandeHistorique entity.
 */
public interface ECommandeHistoriqueSearchRepository extends ElasticsearchRepository<ECommandeHistorique, Long> {
}

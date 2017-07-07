package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.AnnonceHistorique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AnnonceHistorique entity.
 */
public interface AnnonceHistoriqueSearchRepository extends ElasticsearchRepository<AnnonceHistorique, Long> {
}

package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Portefolio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Portefolio entity.
 */
public interface PortefolioSearchRepository extends ElasticsearchRepository<Portefolio, Long> {
}

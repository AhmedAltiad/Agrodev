package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Souscategorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Souscategorie entity.
 */
public interface SouscategorieSearchRepository extends ElasticsearchRepository<Souscategorie, Long> {
}

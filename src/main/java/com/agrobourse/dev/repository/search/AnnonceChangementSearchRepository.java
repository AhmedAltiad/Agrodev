package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.AnnonceChangement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AnnonceChangement entity.
 */
public interface AnnonceChangementSearchRepository extends ElasticsearchRepository<AnnonceChangement, Long> {
}

package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Annonce;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Annonce entity.
 */
public interface AnnonceSearchRepository extends ElasticsearchRepository<Annonce, Long> {
}

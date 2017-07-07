package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Gouvernorat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Gouvernorat entity.
 */
public interface GouvernoratSearchRepository extends ElasticsearchRepository<Gouvernorat, Long> {
}

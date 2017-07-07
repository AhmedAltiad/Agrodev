package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.CommandeDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommandeDetails entity.
 */
public interface CommandeDetailsSearchRepository extends ElasticsearchRepository<CommandeDetails, Long> {
}

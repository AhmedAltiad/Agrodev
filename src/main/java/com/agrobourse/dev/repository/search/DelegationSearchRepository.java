package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Delegation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Delegation entity.
 */
public interface DelegationSearchRepository extends ElasticsearchRepository<Delegation, Long> {
}

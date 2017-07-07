package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.ActiviteAgricole;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActiviteAgricole entity.
 */
public interface ActiviteAgricoleSearchRepository extends ElasticsearchRepository<ActiviteAgricole, Long> {
}

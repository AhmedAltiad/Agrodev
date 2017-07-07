package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.SpecialiteAgricole;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SpecialiteAgricole entity.
 */
public interface SpecialiteAgricoleSearchRepository extends ElasticsearchRepository<SpecialiteAgricole, Long> {
}

package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.GPO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GPO entity.
 */
public interface GPOSearchRepository extends ElasticsearchRepository<GPO, Long> {
}

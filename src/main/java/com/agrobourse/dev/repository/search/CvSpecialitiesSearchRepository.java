package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.CvSpecialities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CvSpecialities entity.
 */
public interface CvSpecialitiesSearchRepository extends ElasticsearchRepository<CvSpecialities, Long> {
}

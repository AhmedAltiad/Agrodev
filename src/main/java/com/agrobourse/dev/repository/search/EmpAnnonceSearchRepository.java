package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.EmpAnnonce;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmpAnnonce entity.
 */
public interface EmpAnnonceSearchRepository extends ElasticsearchRepository<EmpAnnonce, Long> {
}

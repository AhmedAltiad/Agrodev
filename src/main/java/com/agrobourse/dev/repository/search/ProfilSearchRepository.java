package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Profil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Profil entity.
 */
public interface ProfilSearchRepository extends ElasticsearchRepository<Profil, Long> {
}

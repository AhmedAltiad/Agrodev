package com.agrobourse.dev.repository.search;

import com.agrobourse.dev.domain.Produit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Produit entity.
 */
public interface ProduitSearchRepository extends ElasticsearchRepository<Produit, Long> {
}

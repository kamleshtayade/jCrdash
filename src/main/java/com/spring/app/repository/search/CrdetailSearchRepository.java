package com.spring.app.repository.search;

import com.spring.app.domain.Crdetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Crdetail entity.
 */
public interface CrdetailSearchRepository extends ElasticsearchRepository<Crdetail, Long> {
}

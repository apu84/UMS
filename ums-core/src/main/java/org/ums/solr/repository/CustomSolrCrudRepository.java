package org.ums.solr.repository;

import org.springframework.data.solr.repository.SolrCrudRepository;

import java.io.Serializable;

public interface CustomSolrCrudRepository<T, ID extends Serializable> extends SolrCrudRepository<T, ID> {
  long totalDocuments();
}

package org.ums.solr.indexer.reindex;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.manager.ContentManager;
import org.ums.solr.repository.converter.Converter;
import org.ums.solr.repository.document.SearchDocument;

import java.io.Serializable;

public interface ReIndexer<R, M, I extends Serializable, D extends SearchDocument> {
  SolrCrudRepository<D, I> getSolrRepository();

  Converter<R, D> getDocumentConverter();

  ContentManager<R, M, I> getManager();

  void reindex();
}

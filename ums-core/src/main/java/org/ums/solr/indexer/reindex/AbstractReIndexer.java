package org.ums.solr.indexer.reindex;

import org.ums.solr.repository.document.SearchDocument;

import java.io.Serializable;

abstract class AbstractReIndexer<R, M, I extends Serializable, D extends SearchDocument> implements
    ReIndexer<R, M, I, D> {
  public void reindex() {
    getSolrRepository().deleteAll();
    getSolrRepository().save(getDocumentConverter().convert(getManager().getAll()));
  }
}

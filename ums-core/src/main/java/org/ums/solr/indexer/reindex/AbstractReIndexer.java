package org.ums.solr.indexer.reindex;

import org.ums.solr.repository.document.SearchDocument;

import java.io.Serializable;
import java.util.List;

abstract class AbstractReIndexer<R, M, I extends Serializable, D extends SearchDocument> implements
    ReIndexer<R, M, I, D> {
  int mTotalDocumentsToReIndex = 0;

  public void reindex() {
    getSolrRepository().deleteAll();
    List<R> records = getManager().getAll();
    mTotalDocumentsToReIndex = records.size();
    getSolrRepository().save(getDocumentConverter().convert(records));
  }
}

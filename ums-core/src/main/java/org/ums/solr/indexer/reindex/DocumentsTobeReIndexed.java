package org.ums.solr.indexer.reindex;

public class DocumentsTobeReIndexed {
  private ReIndexer[] mReIndexers;

  public DocumentsTobeReIndexed(ReIndexer... pReIndexers) {
    mReIndexers = pReIndexers;
  }

  public ReIndexer[] getReIndexers() {
    return mReIndexers;
  }
}

package org.ums.solr.indexer.reindex;

public class ReIndexStatus {
  private String mDocumentType;
  private long mTotalDocument;
  private long mTotalIndexed;

  public ReIndexStatus(String pDocumentType, long pTotalDocument, long pTotalIndexed) {
    mDocumentType = pDocumentType;
    mTotalDocument = pTotalDocument;
    mTotalIndexed = pTotalIndexed;
  }

  public String getDocumentType() {
    return mDocumentType;
  }

  public long getTotalDocument() {
    return mTotalDocument;
  }

  public long getTotalIndexed() {
    return mTotalIndexed;
  }
}

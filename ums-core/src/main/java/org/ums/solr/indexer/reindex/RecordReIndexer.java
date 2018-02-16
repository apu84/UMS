package org.ums.solr.indexer.reindex;

import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.ContentManager;
import org.ums.solr.repository.CustomSolrCrudRepository;
import org.ums.solr.repository.converter.Converter;
import org.ums.solr.repository.document.lms.RecordDocument;
import org.ums.solr.repository.lms.RecordRepository;

public class RecordReIndexer extends AbstractReIndexer<Record, MutableRecord, Long, RecordDocument> {
  private RecordRepository mSolrCrudRepository;
  private Converter<Record, RecordDocument> mDocumentConverter;
  private ContentManager<Record, MutableRecord, Long> mContentManager;

  public RecordReIndexer(final RecordRepository pSolrRepository,
                         final Converter<Record, RecordDocument> pDocumentConverter,
                         final ContentManager<Record, MutableRecord, Long> pContentManager) {
    mSolrCrudRepository = pSolrRepository;
    mDocumentConverter = pDocumentConverter;
    mContentManager = pContentManager;
  }

  @Override
  public CustomSolrCrudRepository<RecordDocument, Long> getSolrRepository() {
    return mSolrCrudRepository;
  }

  @Override
  public Converter<Record, RecordDocument> getDocumentConverter() {
    return mDocumentConverter;
  }

  @Override
  public ContentManager<Record, MutableRecord, Long> getManager() {
    return mContentManager;
  }

  @Override
  public ReIndexStatus status() {
    return new ReIndexStatus(RecordDocument.DOCUMENT_TYPE,
        mTotalDocumentsToReIndex,
        mSolrCrudRepository.totalDocuments());
  }
}

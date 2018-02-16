package org.ums.solr.indexer.reindex;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.ContentManager;
import org.ums.solr.repository.CustomSolrCrudRepository;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.converter.Converter;
import org.ums.solr.repository.document.EmployeeDocument;

public class EmployeeReIndexer extends AbstractReIndexer<Employee, MutableEmployee, String, EmployeeDocument> {
  private EmployeeRepository mSolrCrudRepository;
  private Converter<Employee, EmployeeDocument> mDocumentConverter;
  private ContentManager<Employee, MutableEmployee, String> mContentManager;

  public EmployeeReIndexer(final EmployeeRepository pSolrRepository,
                           final Converter<Employee, EmployeeDocument> pDocumentConverter,
                           final ContentManager<Employee, MutableEmployee, String> pContentManager) {
    mSolrCrudRepository = pSolrRepository;
    mDocumentConverter = pDocumentConverter;
    mContentManager = pContentManager;
  }

  @Override
  public CustomSolrCrudRepository<EmployeeDocument, String> getSolrRepository() {
    return mSolrCrudRepository;
  }

  @Override
  public Converter<Employee, EmployeeDocument> getDocumentConverter() {
    return mDocumentConverter;
  }

  @Override
  public ContentManager<Employee, MutableEmployee, String> getManager() {
    return mContentManager;
  }

  @Override
  public ReIndexStatus status() {
    return new ReIndexStatus(EmployeeDocument.DOCUMENT_TYPE,
        mTotalDocumentsToReIndex,
        mSolrCrudRepository.totalDocuments());
  }
}

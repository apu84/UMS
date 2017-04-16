package org.ums.solr.indexer.resolver.lms;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.library.Record;
import org.ums.manager.EmployeeManager;
import org.ums.manager.library.RecordManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.resolver.EntityResolver;
import org.ums.solr.indexer.resolver.EntityResolverFactory;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.RecordRepository;
import org.ums.solr.repository.converter.SimpleConverter;
import org.ums.solr.repository.document.EmployeeDocument;
import org.ums.solr.repository.document.lms.RecordDocument;

/**
 * Created by Ifti on 15-Apr-17.
 */
public class RecordResolver implements EntityResolver {
  private RecordManager mRecordManager;
  private RecordRepository mRecordRepository;

  public RecordResolver(RecordManager pRecordManager, RecordRepository pRecordRepository) {
    this.mRecordManager = pRecordManager;
    this.mRecordRepository = pRecordRepository;
  }

  @Override
  public String getEntityType() {
    return "lms-record";
  }

  @Override
  public void resolve(Index pIndex) {
    if(!pIndex.isDeleted()) {
      Record record = mRecordManager.get(Long.valueOf(pIndex.getEntityId()));
      SimpleConverter<Record, RecordDocument> converter = new SimpleConverter<>(Record.class, RecordDocument.class);
      RecordDocument recordDocument = converter.convert(record);
      mRecordRepository.save(recordDocument);
    }
    else {
      mRecordRepository.delete(pIndex.getEntityId());
    }
  }
}

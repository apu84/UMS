package org.ums.solr.repository.transaction.lms;

import org.ums.decorator.EmployeeDaoDecorator;
import org.ums.decorator.library.RecordDaoDecorator;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.solr.indexer.PersistentIndex;
import org.ums.solr.indexer.model.MutableIndex;

/**
 * Created by Ifti on 15-Apr-17.
 */
public class RecordTransaction extends RecordDaoDecorator {

  @Override
  public Long create(MutableRecord pMutable) {
    Long id = getManager().create(pMutable);
    generateIndexEntity(id, false);
    return id;
  }

  private void generateIndexEntity(Long pId, boolean isDeleted) {
    MutableIndex index = new PersistentIndex();
    index.setEntityId(pId.toString());
    index.setEntityType("lms-record");
    index.setIsDeleted(isDeleted);
    index.create();
  }

}

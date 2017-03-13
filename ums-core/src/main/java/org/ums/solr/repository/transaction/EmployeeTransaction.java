package org.ums.solr.repository.transaction;

import org.ums.decorator.EmployeeDaoDecorator;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.solr.indexer.PersistentIndex;
import org.ums.solr.indexer.model.MutableIndex;

public class EmployeeTransaction extends EmployeeDaoDecorator {

  @Override
  public String create(MutableEmployee pMutable) {
    String id = getManager().create(pMutable);
    generateIndexEntity(id, false);
    return id;
  }

  @Override
  public int update(MutableEmployee pMutable) {
    int update = getManager().update(pMutable);
    generateIndexEntity(pMutable.getId(), false);
    return update;
  }

  @Override
  public int delete(MutableEmployee pMutable) {
    int delete = getManager().delete(pMutable);
    generateIndexEntity(pMutable.getId(), true);
    return delete;
  }

  private void generateIndexEntity(String pId, boolean isDeleted) {
    MutableIndex index = new PersistentIndex();
    index.setEntityId(pId);
    index.setEntityType("employee");
    index.setIsDeleted(isDeleted);
    index.commit(false);
  }
}

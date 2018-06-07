package org.ums.solr.repository.transaction.meeting;

import org.ums.decorator.meeting.AgendaResolutionDaoDecorator;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.solr.indexer.PersistentIndex;
import org.ums.solr.indexer.model.MutableIndex;

public class AgendaResolutionTransaction extends AgendaResolutionDaoDecorator {

  @Override
  public Long create(MutableAgendaResolution pMutable) {
    Long id = getManager().create(pMutable);
    generateIndexEntity(id, false);
    return id;
  }

  @Override
  public int update(MutableAgendaResolution pMutable) {
    int update = getManager().update(pMutable);
    generateIndexEntity(pMutable.getId(), false);
    return update;
  }

  @Override
  public int delete(MutableAgendaResolution pMutable) {
    int delete = getManager().delete(pMutable);
    generateIndexEntity(pMutable.getId(), true);
    return delete;
  }

  private void generateIndexEntity(Long pId, boolean isDeleted) {
    MutableIndex index = new PersistentIndex();
    index.setEntityId(pId.toString());
    index.setEntityType("meeting-record");
    index.setIsDeleted(isDeleted);
    index.create();
  }
}

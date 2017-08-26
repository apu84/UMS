package org.ums.solr.repository.transaction.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.decorator.meeting.AgendaResolutionDaoDecorator;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.solr.indexer.PersistentIndex;
import org.ums.solr.indexer.model.MutableIndex;

public class AgendaResolutionTransaction extends AgendaResolutionDaoDecorator {

  @Override
  public Long saveAgendaResolution(MutableAgendaResolution pMutableAgendaResolution) {
    Long id = getManager().saveAgendaResolution(pMutableAgendaResolution);
    generateIndexEntity(id, false);
    return id;
  }

  @Override
  public int updateAgendaResolution(MutableAgendaResolution pMutableAgendaResolution) {
    int update = getManager().updateAgendaResolution(pMutableAgendaResolution);
    generateIndexEntity(pMutableAgendaResolution.getId(), false);
    return update;
  }

  @Override
  public int deleteAgendaResolution(MutableAgendaResolution pMutableAgendaResolution) {
    int delete = getManager().deleteAgendaResolution(pMutableAgendaResolution);
    generateIndexEntity(pMutableAgendaResolution.getId(), true);
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

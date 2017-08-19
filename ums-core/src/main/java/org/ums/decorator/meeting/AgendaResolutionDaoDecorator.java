package org.ums.decorator.meeting;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.meeting.AgendaResolutionManager;

import java.util.List;

public class AgendaResolutionDaoDecorator extends
    ContentDaoDecorator<AgendaResolution, MutableAgendaResolution, Long, AgendaResolutionManager> implements
    AgendaResolutionManager {
  @Override
  public Long saveAgendaResolution(MutableAgendaResolution pMutableAgendaResolution) {
    return getManager().saveAgendaResolution(pMutableAgendaResolution);
  }

  @Override
  public List<AgendaResolution> getAgendaResolution(Long pScheduleId) {
    return getManager().getAgendaResolution(pScheduleId);
  }

  @Override
  public int updateAgendaResolution(MutableAgendaResolution pMutableAgendaResolution) {
    return getManager().updateAgendaResolution(pMutableAgendaResolution);
  }

  @Override
  public int deleteAgendaResolution(MutableAgendaResolution pMutableAgendaResolution) {
    return getManager().deleteAgendaResolution(pMutableAgendaResolution);
  }
}

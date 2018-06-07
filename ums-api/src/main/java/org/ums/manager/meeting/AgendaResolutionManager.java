package org.ums.manager.meeting;

import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AgendaResolutionManager extends ContentManager<AgendaResolution, MutableAgendaResolution, Long> {

  List<AgendaResolution> getAgendaResolution(final Long pScheduleId);
}

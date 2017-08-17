package org.ums.domain.model.mutable.meeting;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAgendaResolution extends AgendaResolution, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setAgendaNo(final String pAgendaNo);

  void setAgenda(final String pAgenda);

  void setResolution(final String pResolution);

  void setScheduleId(final Long pScheduleId);
}

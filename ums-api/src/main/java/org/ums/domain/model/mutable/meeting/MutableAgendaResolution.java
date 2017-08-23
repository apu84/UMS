package org.ums.domain.model.mutable.meeting;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAgendaResolution extends AgendaResolution, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setAgendaNo(final String pAgendaNo);

  void setAgenda(final String pAgenda);

  void setAgendaEditor(final String pAgendaEditor);

  void setResolution(final String pResolution);

  void setResolutionEditor(final String pResolutionEditor);

  void setScheduleId(final Long pScheduleId);
}

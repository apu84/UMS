package org.ums.domain.model.immutable.meeting;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;

import java.io.Serializable;

public interface AgendaResolution extends Serializable, EditType<MutableAgendaResolution>, Identifier<Long>,
    LastModifier {

  String getAgendaNo();

  String getAgenda();

  String getAgendaEditor();

  String getResolution();

  String getResolutionEditor();

  Long getScheduleId();
}

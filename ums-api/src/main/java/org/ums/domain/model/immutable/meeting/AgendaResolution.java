package org.ums.domain.model.immutable.meeting;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;

import java.io.Serializable;

public interface AgendaResolution extends Serializable, EditType<MutableAgendaResolution>, LastModifier,
    Identifier<Long> {

  String getAgendaNo();

  String getAgenda();

  String getAgendaEditor();

  String getPlainAgenda();

  String getResolution();

  String getResolutionEditor();

  String getPlainResolution();

  Long getScheduleId();
}

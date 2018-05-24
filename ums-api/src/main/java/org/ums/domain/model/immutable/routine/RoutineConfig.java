package org.ums.domain.model.immutable.routine;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.enums.routine.DayType;

import java.io.Serializable;
import java.time.LocalTime;

public interface RoutineConfig extends Serializable, EditType<MutableRoutineConfig>, LastModifier, Identifier<Long> {

  Program getProgram();

  Integer getProgramId();

  Semester getSemester();

  Integer getSemesterId();

  DayType getDayFrom();

  DayType getDayTo();

  LocalTime getStartTime();

  LocalTime getEndTime();

  Integer getDuration();

  String getLastModified();
}

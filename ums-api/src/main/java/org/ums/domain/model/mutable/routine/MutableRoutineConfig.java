package org.ums.domain.model.mutable.routine;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.ProgramType;
import org.ums.enums.routine.DayType;

import java.time.LocalTime;

public interface MutableRoutineConfig extends RoutineConfig, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setProgramType(ProgramType pProgramType);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setDayFrom(DayType pDayFrom);

  void setDayTo(DayType pDayTo);

  void setStartTime(LocalTime pStartTime);

  void setEndTime(LocalTime pEndTime);

  void setDuration(Integer pDuration);

  void setLastModified(String pLastModified);
}

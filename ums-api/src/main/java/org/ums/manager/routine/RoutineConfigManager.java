package org.ums.manager.routine;

import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.manager.ContentManager;

public interface RoutineConfigManager extends ContentManager<RoutineConfig, MutableRoutineConfig, Long> {
  RoutineConfig get(Semester pSemester, Program pProgram);
}

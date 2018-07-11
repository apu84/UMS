package org.ums.manager.routine;

import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.manager.ContentManager;

public interface RoutineConfigManager extends ContentManager<RoutineConfig, MutableRoutineConfig, Long> {
  RoutineConfig get(Integer pSemesterId, org.ums.enums.ProgramType pProgramType);
}

package org.ums.decorator.routine;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.manager.routine.RoutineConfigManager;

public class RoutineConfigDaoDecorator extends
    ContentDaoDecorator<RoutineConfig, MutableRoutineConfig, Long, RoutineConfigManager> implements
    RoutineConfigManager {

  @Override
  public RoutineConfig get(Integer pSemesterId, org.ums.enums.ProgramType pProgramType) {
    return getManager().get(pSemesterId, pProgramType);
  }
}

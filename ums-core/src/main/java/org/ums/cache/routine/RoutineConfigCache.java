package org.ums.cache.routine;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.manager.CacheManager;
import org.ums.manager.routine.RoutineConfigManager;

public class RoutineConfigCache extends ContentCache<RoutineConfig, MutableRoutineConfig, Long, RoutineConfigManager>
    implements RoutineConfigManager {
  private CacheManager<RoutineConfig, Long> manager;

  public RoutineConfigCache(CacheManager<RoutineConfig, Long> manager) {
    this.manager = manager;
  }

  @Override
  protected CacheManager<RoutineConfig, Long> getCacheManager() {
    return manager;
  }

  @Override
  public RoutineConfig get(Integer pSemesterId, Integer pProgramId) {
    return getManager().get(pSemesterId, pProgramId);
  }
}

package org.ums.cache.meeting;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.manager.CacheManager;
import org.ums.manager.meeting.AgendaResolutionManager;

import java.util.List;

public class AgendaResolutionCache extends
    ContentCache<AgendaResolution, MutableAgendaResolution, Long, AgendaResolutionManager> implements
    AgendaResolutionManager {

  CacheManager<AgendaResolution, Long> mCacheManager;

  public AgendaResolutionCache(CacheManager<AgendaResolution, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AgendaResolution, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<AgendaResolution> getAgendaResolution(Long pScheduleId) {
    return getManager().getAgendaResolution(pScheduleId);
  }
}

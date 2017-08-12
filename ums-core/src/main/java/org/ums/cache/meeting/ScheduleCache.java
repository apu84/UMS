package org.ums.cache.meeting;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.CacheManager;
import org.ums.manager.meeting.ScheduleManager;

public class ScheduleCache extends ContentCache<Schedule, MutableSchedule, Integer, ScheduleManager> implements
    ScheduleManager {

  private CacheManager<Schedule, Integer> mCacheManager;

  public ScheduleCache(CacheManager<Schedule, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Schedule, Integer> getCacheManager() {
    return mCacheManager;
  }
}

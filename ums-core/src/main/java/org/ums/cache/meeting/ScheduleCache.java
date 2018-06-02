package org.ums.cache.meeting;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.CacheManager;
import org.ums.manager.meeting.ScheduleManager;

import java.util.List;

public class ScheduleCache extends ContentCache<Schedule, MutableSchedule, Long, ScheduleManager> implements
    ScheduleManager {

  private CacheManager<Schedule, Long> mCacheManager;

  public ScheduleCache(CacheManager<Schedule, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Schedule, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public Schedule get(int pMeetingType, int meetingNo) {
    return getManager().get(pMeetingType, meetingNo);
  }

  @Override
  public int getNextMeetingNo(int pMeetingTypeId) {
    return getManager().getNextMeetingNo(pMeetingTypeId);
  }

  @Override
  public List<Schedule> get(int pMeetingTypeId) {
    return getManager().get(pMeetingTypeId);
  }
}

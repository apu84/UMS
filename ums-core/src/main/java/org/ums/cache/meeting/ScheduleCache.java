package org.ums.cache.meeting;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;
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
  public int saveMeetingSchedule(MutableSchedule pMeetingSchedule) {
    return getManager().saveMeetingSchedule(pMeetingSchedule);
  }

  @Override
  public Schedule getMeetingSchedule(int pMeetingType, int meetingNo) {
    return getManager().getMeetingSchedule(pMeetingType, meetingNo);
  }

  @Override
  public List<Schedule> getAllMeetingSchedule(int pMeetingTypeId) {
    return getManager().getAllMeetingSchedule(pMeetingTypeId);
  }

  @Override
  public int updateMeetingSchedule(MutableSchedule pMeetingSchedule) {
    return getManager().updateMeetingSchedule(pMeetingSchedule);
  }
}

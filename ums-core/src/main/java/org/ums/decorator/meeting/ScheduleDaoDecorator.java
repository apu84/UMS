package org.ums.decorator.meeting;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.meeting.ScheduleManager;

import java.util.List;

public class ScheduleDaoDecorator extends ContentDaoDecorator<Schedule, MutableSchedule, Long, ScheduleManager>
    implements ScheduleManager {

  @Override
  public Schedule get(int pMeetingTypeId, int meetingNo) {
    return getManager().get(pMeetingTypeId, meetingNo);
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

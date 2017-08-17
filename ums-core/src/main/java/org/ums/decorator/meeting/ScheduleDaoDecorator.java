package org.ums.decorator.meeting;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;
import org.ums.manager.meeting.ScheduleManager;

public class ScheduleDaoDecorator extends ContentDaoDecorator<Schedule, MutableSchedule, Long, ScheduleManager>
    implements ScheduleManager {
  @Override
  public int saveMeetingSchedule(MutableSchedule pMeetingSchedule) {
    return getManager().saveMeetingSchedule(pMeetingSchedule);
  }

  @Override
  public Schedule getMeetingSchedule(int pMeetingTypeId, int meetingNo) {
    return getManager().getMeetingSchedule(pMeetingTypeId, meetingNo);
  }

  @Override
  public int updateMeetingSchedule(MutableSchedule pMeetingSchedule) {
    return getManager().updateMeetingSchedule(pMeetingSchedule);
  }
}

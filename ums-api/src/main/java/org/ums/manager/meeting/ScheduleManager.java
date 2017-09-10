package org.ums.manager.meeting;

import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ScheduleManager extends ContentManager<Schedule, MutableSchedule, Long> {

  int saveMeetingSchedule(final MutableSchedule pMeetingSchedule);

  Schedule getMeetingSchedule(final int pMeetingTypeId, final int meetingNo);

  List<Schedule> getAllMeetingSchedule(final int pMeetingTypeId);

  int updateMeetingSchedule(final MutableSchedule pMeetingSchedule);

  int getNextMeetingNo(final int pMeetingTypeId);
}

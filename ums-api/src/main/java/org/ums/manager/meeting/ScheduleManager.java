package org.ums.manager.meeting;

import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ScheduleManager extends ContentManager<Schedule, MutableSchedule, Long> {

  Schedule get(final int pMeetingTypeId, final int meetingNo);

  int getNextMeetingNo(final int pMeetingTypeId);

  List<Schedule> get(final int pMeetingTypeId);
}

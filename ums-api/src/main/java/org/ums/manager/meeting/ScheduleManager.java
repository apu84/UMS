package org.ums.manager.meeting;

import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.ContentManager;

public interface ScheduleManager extends ContentManager<Schedule, MutableSchedule, Integer> {
}

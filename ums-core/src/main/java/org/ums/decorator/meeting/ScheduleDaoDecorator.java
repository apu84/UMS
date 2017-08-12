package org.ums.decorator.meeting;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.meeting.ScheduleManager;

public class ScheduleDaoDecorator extends ContentDaoDecorator<Schedule, MutableSchedule, Integer, ScheduleManager>
    implements ScheduleManager {
}

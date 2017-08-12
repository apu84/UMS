package org.ums.domain.model.mutable.meeting;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.meeting.MeetingType;

import java.sql.Time;
import java.util.Date;

public interface MutableSchedule extends Schedule, Editable<Integer>, MutableIdentifier<Integer>, MutableLastModifier {

  void setMeetingType(final MeetingType pMeetingType);

  void setMeetingTypeId(final int pMeetingTypeId);

  void setMeetingNo(final int pMeetingNo);

  void setMeetingRefNo(final String pMeetingRefNo);

  void setMeetingDate(final Date pMeetingDate);

  void setMeetingTime(final Time pMeetingTime);

  void setMeetingRoomNo(final String pMeetingRoomNo);
}

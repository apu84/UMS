package org.ums.domain.model.mutable.meeting;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.meeting.MeetingType;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public interface MutableSchedule extends Schedule, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setMeetingType(final MeetingType pMeetingType);

  void setMeetingTypeId(final int pMeetingTypeId);

  void setMeetingNo(final int pMeetingNo);

  void setMeetingRefNo(final String pMeetingRefNo);

  void setMeetingDateTime(final Timestamp pMeetingDateTime);

  void setMeetingRoomNo(final String pMeetingRoomNo);
}

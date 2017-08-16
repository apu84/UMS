package org.ums.meeting;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;

@Component
public class ScheduleBuilder implements Builder<Schedule, MutableSchedule> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Schedule pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableSchedule pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setMeetingTypeId(pJsonObject.getJsonObject("meetingType").getInt("id"));
    pMutable.setMeetingNo(pJsonObject.getInt("meetingNo"));
    pMutable.setMeetingRefNo(pJsonObject.getString("meetingRefNo"));
    pMutable.setMeetingDateTime(Timestamp.valueOf(pJsonObject.getString("meetingDateTime")));
    pMutable.setMeetingRoomNo(pJsonObject.getString("meetingRoom"));
  }
}

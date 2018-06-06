package org.ums.meeting;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleBuilder implements Builder<Schedule, MutableSchedule> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Schedule pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    JsonObjectBuilder meetingBuilder = Json.createObjectBuilder();
    meetingBuilder.add("id", pReadOnly.getMeetingTypeId()).add("name",
        MeetingType.get(pReadOnly.getMeetingTypeId()).getLabel());
    pBuilder.add("type", meetingBuilder);
    pBuilder.add("meetingNo", pReadOnly.getMeetingNo());
    pBuilder.add("refNo", pReadOnly.getMeetingRefNo());
    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
    String outputString = outputFormat.format(pReadOnly.getMeetingDateTime());
    pBuilder.add("datetime", outputString);
    pBuilder.add("room", pReadOnly.getMeetingRoomNo());
    if(pReadOnly.getMeetingDateTime().before(new Date())){
      pBuilder.add("edit", false);
    }
    else{
      pBuilder.add("edit", true);
    }
  }

  @Override
  public void build(MutableSchedule pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
    Date date = null;
    try {
      date = dateFormat.parse(pJsonObject.getString("datetime"));
    } catch(ParseException e) {
      e.printStackTrace();
    }
    long time = date.getTime();
    pMutable.setId(pJsonObject.containsKey("id") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setMeetingTypeId(pJsonObject.getJsonObject("type").getInt("id"));
    pMutable.setMeetingNo(pJsonObject.getInt("meetingNo"));
    pMutable.setMeetingRefNo(pJsonObject.getString("refNo"));
    pMutable.setMeetingDateTime(new Timestamp(time));
    pMutable.setMeetingRoomNo(pJsonObject.getString("room"));
  }
}

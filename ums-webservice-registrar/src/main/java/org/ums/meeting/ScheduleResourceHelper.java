package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;
import org.ums.manager.ContentManager;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.persistent.model.meeting.PersistentSchedule;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleResourceHelper extends ResourceHelper<Schedule, MutableSchedule, Long> {

  @Autowired
  ScheduleManager mManager;

  @Autowired
  ScheduleBuilder mBuilder;

  public JsonObject getScheduleInformation(final int pMeetingTypeId, final int pMeetingNo, final UriInfo pUriInfo) {
    Schedule schedule = new PersistentSchedule();
    try {
      schedule = mManager.getMeetingSchedule(pMeetingTypeId, pMeetingNo);
    } catch(EmptyResultDataAccessException e) {
    }

    return buildJson(schedule, pUriInfo);
  }

  public JsonObject getAllScheduleInformation(final int pMeetingTypeId, final UriInfo pUriInfo) {
    List<Schedule> schedule = new ArrayList<>();
    try {
      schedule = mManager.getAllMeetingSchedule(pMeetingTypeId);
    } catch(EmptyResultDataAccessException e) {
    }

    return toJson(schedule, pUriInfo);
  }

  public Response saveSchedule(JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableSchedule mutableSchedule = new PersistentSchedule();
    LocalCache localCache = new LocalCache();
    mBuilder.build(mutableSchedule, pJsonObject.getJsonObject("entries"), localCache);
    mManager.saveMeetingSchedule(mutableSchedule);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject buildJson(Schedule pSchedule, UriInfo pUriInfo) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    if(pSchedule.getId() != null) {
      children.add(toJson(pSchedule, pUriInfo, localCache));
    }
    jsonObject.add("entries", children);
    localCache.invalidate();
    return jsonObject.build();
  }

  private JsonObject toJson(List<Schedule> pSchedule, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Schedule schedule : pSchedule) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, schedule, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Schedule, MutableSchedule, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Schedule, MutableSchedule> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Schedule pReadonly) {
    return pReadonly.getLastModified();
  }
}

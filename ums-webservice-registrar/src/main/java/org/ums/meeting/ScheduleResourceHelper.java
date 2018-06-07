package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.ContentManager;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.persistent.model.meeting.PersistentSchedule;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ScheduleResourceHelper extends ResourceHelper<Schedule, MutableSchedule, Long> {

  @Autowired
  ScheduleManager mManager;

  @Autowired
  ScheduleBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableSchedule mutableSchedule = new PersistentSchedule();
    LocalCache localCache = new LocalCache();
    mBuilder.build(mutableSchedule, pJsonObject.getJsonObject("entries"), localCache);
    mManager.create(mutableSchedule);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableSchedule mutableSchedule = new PersistentSchedule();
    mBuilder.build(mutableSchedule, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableSchedule);
    localCache.invalidate();
    return Response.ok().build();
  }

  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableSchedule mutableSchedule = (MutableSchedule) mManager.get(id);
    mManager.delete(mutableSchedule);
    localCache.invalidate();
    return Response.noContent().build();
  }

  public JsonObject getNextMeetingNo(final int pMeetingTypeId, final UriInfo pUriInfo) {
    int meetingNo = 0;
    meetingNo = mManager.getNextMeetingNo(pMeetingTypeId);
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("nextMeetingNumber", meetingNo + 1);
    return jsonObjectBuilder.build();
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

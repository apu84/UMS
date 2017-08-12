package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.manager.ContentManager;
import org.ums.manager.meeting.ScheduleManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ScheduleResourceHelper extends ResourceHelper<Schedule, MutableSchedule, Integer> {

  @Autowired
  ScheduleManager mManager;

  @Autowired
  ScheduleBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Schedule, MutableSchedule, Integer> getContentManager() {
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

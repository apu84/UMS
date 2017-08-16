package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableScheduleResource extends Resource {

  @Autowired
  ScheduleResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveMeetingSchedule(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveSchedule(pJsonObject, mUriInfo);
  }
}

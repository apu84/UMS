package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.DeleteLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableScheduleResource extends Resource {

  @Autowired
  private ScheduleResourceHelper mHelper;

  @POST
  @PostLog(message = "Created a meeting schedule")
  public Response create(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject)
      throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @PutLog(message = "Updated a meeting schedule")
  public Response update(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mHelper.update(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @DeleteLog(message = "Deleted a meeting schedule")
  public Response delete(@Context HttpServletRequest pHttpServletRequest, final @PathParam("object-id") String pObjectId) {
    System.out.println(pObjectId);
    return mHelper.delete(Long.parseLong(pObjectId), mUriInfo);
  }
}

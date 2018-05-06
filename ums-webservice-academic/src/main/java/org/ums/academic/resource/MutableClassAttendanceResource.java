package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.logs.DeleteLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableClassAttendanceResource extends Resource {

  @Autowired
  ClassAttendanceResourceHelper mResourceHelper;

  @POST
  @PostLog(message = "Created new class attendance")
  public Response createNewClassAttendance(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mResourceHelper.saveNewAttendance(pJsonObject);
  }

  @PUT
  @PutLog(message = "Updated class attendance")
  public Response updateClassAttendance(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader) {
    return mResourceHelper.updateClassAttendance(pJsonObject);
  }

  @DELETE
  @DeleteLog(message = "Deleted class attendance")
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteClassAttendance(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("object-id") String objectId) {
    return mResourceHelper.deleteClassAttendance(objectId);
  }

}

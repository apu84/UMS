package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 31-Oct-16.
 */
public class MutableClassAttendanceResource extends Resource {

  @Autowired
  ClassAttendanceResourceHelper mResourceHelper;

  @POST
  @UmsLogMessage(message = "::: Create new Class Attendance :::")
  public Response createNewClassAttendance(@Context HttpServletRequest pHttpServletRequest,
      @HeaderParam("user-agent") String userAgent, final JsonObject pJsonObject) {
    return mResourceHelper.saveNewAttendance(pJsonObject);
  }

  @PUT
  @UmsLogMessage(message = "::: Update Class Attendance :::")
  public Response updateClassAttendance(@Context HttpServletRequest pHttpServletRequest,
      @HeaderParam("user-agent") String userAgent, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) {
    return mResourceHelper.updateClassAttendance(pJsonObject);
  }

  @DELETE
  @UmsLogMessage(message = "::: Delete Class Attendance :::")
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteClassAttendance(@Context HttpServletRequest pHttpServletRequest,
      @HeaderParam("user-agent") String userAgent, final @PathParam("object-id") String objectId) {
    return mResourceHelper.deleteClassAttendance(objectId);
  }

}

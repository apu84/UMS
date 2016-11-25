package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.common.academic.resource.helper.ClassRoomResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
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
  public Response createNewClassAttendance(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.saveNewAttendance(pJsonObject);
  }

  @PUT
  public Response updateClassAttendance(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.updateClassAttendance(pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteClassAttendance(final @PathParam("object-id") String objectId)
      throws Exception {
    return mResourceHelper.deleteClassAttendance(objectId);
  }

}

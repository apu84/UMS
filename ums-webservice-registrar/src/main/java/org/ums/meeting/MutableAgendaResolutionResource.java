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

public class MutableAgendaResolutionResource extends Resource {

  @Autowired
  AgendaResolutionResourceHelper mHelper;

  @POST
  @PostLog(message = "Created a meeting agenda resolution")
  public Response saveMeetingAgendaResolution(@Context HttpServletRequest pHttpServletRequest,
      final JsonObject pJsonObject) throws Exception {
    return mHelper.saveAgendaResolution(pJsonObject, mUriInfo);
  }

  @PUT
  @PutLog(message = "Updated a meeting agenda resolution")
  public Response updateMeetingAgendaResolution(@Context HttpServletRequest pHttpServletRequest,
      final JsonObject pJsonObject) throws Exception {
    return mHelper.updateAgendaResolution(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @DeleteLog(message = "Deleted a meeting agenda resolution")
  public Response deleteMeetingAgendaResolution(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("object-id") String pObjectId) throws Exception {
    return mHelper.deleteAgendaResolution(pObjectId, mUriInfo);
  }

}

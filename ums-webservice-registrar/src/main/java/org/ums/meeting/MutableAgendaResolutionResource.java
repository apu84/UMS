package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class MutableAgendaResolutionResource extends Resource {

  @Autowired
  AgendaResolutionResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveMeetingAgendaResolution(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveAgendaResolution(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update")
  public Response updateMeetingAgendaResolution(final JsonObject pJsonObject) throws Exception {
    return mHelper.updateAgendaResolution(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path("/delete/{id}")
  public Response deleteMeetingAgendaResolution(final @PathParam("id") String pId) throws Exception {
    return mHelper.deleteAgendaResolution(pId, mUriInfo);
  }

}

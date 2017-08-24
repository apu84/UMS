package org.ums.meeting;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("meeting/agendaResolution")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AgendaResolutionResource extends MutableAgendaResolutionResource {

  @GET
  @Path("/get/scheduleId/{schedule-id}")
  public JsonObject getMeetingAgendaResolution(final @Context Request pRequest,
      final @PathParam("schedule-id") String pScheduleId) throws Exception {
    return mHelper.getAgendaResolution(pScheduleId, mUriInfo);
  }
}

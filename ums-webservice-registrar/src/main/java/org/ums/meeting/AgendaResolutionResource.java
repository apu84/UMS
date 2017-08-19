package org.ums.meeting;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("meeting/agendaResolution")
public class AgendaResolutionResource extends MutableAgendaResolutionResource {

  @GET
  @Path("/get/scheduleId/{schedule-id}")
  public JsonObject getMeetingAgendaResolution(final @PathParam("schedule-id") String pScheduleId,
      final @Context Request pRequest) throws Exception {
    return mHelper.getAgendaResolution(pScheduleId, mUriInfo);
  }
}

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

  @GET
  @Path("/all/ipp/{item-per-page}/page/{page}/order/{order}")
  public JsonObject getAllForPagination(final @Context Request pRequest,
      final @PathParam("item-per-page") int pItemPerPage, final @PathParam("page") int pPage,
      final @PathParam("order") String pOrder, final @QueryParam("filter") String pFilter) throws Exception {
    return mHelper.searchAgendaResolution(pPage, pItemPerPage, pFilter, mUriInfo);
  }
}

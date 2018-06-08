package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("meeting/agendaResolution")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AgendaResolutionResource extends MutableAgendaResolutionResource {

  @Autowired
  private AgendaResolutionResourceHelper mHelper;

  @GET
  @Path("/scheduleId/{schedule-id}")
  @GetLog(message = "Get meeting agenda resolution list")
  public JsonObject getMeetingAgendaResolution(@Context HttpServletRequest pHttpServletRequest,
      final @Context Request pRequest, final @PathParam("schedule-id") String pScheduleId) throws Exception {
    return mHelper.getAgendaResolution(pScheduleId, mUriInfo);
  }

  @GET
  @Path("/all/ipp/{item-per-page}/page/{page}/order/{order}")
  @GetLog(message = "Get meeting agenda resolution list with pagination")
  public JsonObject getAllForPagination(@Context HttpServletRequest pHttpServletRequest,
      final @Context Request pRequest, final @PathParam("item-per-page") int pItemPerPage,
      final @PathParam("page") int pPage, final @PathParam("order") String pOrder,
      final @QueryParam("filter") String pFilter) throws Exception {
    return mHelper.searchAgendaResolution(pPage, pItemPerPage, pFilter, mUriInfo);
  }
}

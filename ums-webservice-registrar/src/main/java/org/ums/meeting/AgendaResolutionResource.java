package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.report.MeetingMinutesGenerator;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Path("meeting/agendaResolution")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AgendaResolutionResource extends MutableAgendaResolutionResource {

  @Autowired
  private AgendaResolutionResourceHelper mHelper;

  @Autowired
  private MeetingMinutesGenerator mMeetingMinutesGenerator;

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

  @GET
  @Path("/meetingType/{meeting-type}/meetingNo/{meeting-no}/printType/{print-type}")
  @Produces("application/pdf")
  @GetLog(message = "Generate meeting agenda and resolution pdf")
  public StreamingOutput getMeetingMinutes(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("meeting-type") int pMeetingId, final @PathParam("meeting-no") int pMeetingNo,
      final @PathParam("print-type") int pPrintingSection, final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mMeetingMinutesGenerator.createMeetingMinutes(pMeetingId, pMeetingNo, pPrintingSection, pOutputStream);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}

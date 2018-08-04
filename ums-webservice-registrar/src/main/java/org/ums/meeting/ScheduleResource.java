package org.ums.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("meeting/schedule")
public class ScheduleResource extends MutableScheduleResource {

  @Autowired
  private ScheduleResourceHelper mHelper;

  @GET
  @GetLog(message = "Get all meeting schedule list")
  public JsonObject get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest)
      throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/nextMeetingNo/meetingType/{meetingType}")
  public JsonObject getNextMeetingNo(final @PathParam("meetingType") int pMeetingTypeId, final @Context Request pRequest)
      throws Exception {
    return mHelper.getNextMeetingNo(pMeetingTypeId, mUriInfo);
  }

  @GET
  @Path("/meetingType/{meetingType}")
  public JsonObject getMeetingInfo(final @PathParam("meetingType") int pMeetingTypeId, final @Context Request pRequest)
      throws Exception {
    return mHelper.getMeetingInfo(pMeetingTypeId, mUriInfo);
  }
}

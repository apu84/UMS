package org.ums.meeting;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("meeting/schedule")
public class ScheduleResource extends MutableScheduleResource {

  @GET
  @Path("/get/meetingType/{meetingType}/meetingNo/{meetingNo}")
  public JsonObject getMeetingSchedule(final @PathParam("meetingType") int pMeetingTypeId,
      final @PathParam("meetingNo") int pMeetingNo, final @Context Request pRequest) throws Exception {
    return mHelper.getScheduleInformation(pMeetingTypeId, pMeetingNo, mUriInfo);
  }

  @GET
  @Path("/get/meetingType/{meetingType}")
  public JsonObject getNextMeetingNo(final @PathParam("meetingType") int pMeetingTypeId, final @Context Request pRequest)
      throws Exception {
    return mHelper.getNextMeetingNo(pMeetingTypeId, mUriInfo);
  }

  @GET
  @Path("/getAll/meetingType/{meetingType}")
  public JsonObject getAllMeetingSchedule(final @PathParam("meetingType") int pMeetingTypeId,
      final @Context Request pRequest) throws Exception {
    return mHelper.getAllScheduleInformation(pMeetingTypeId, mUriInfo);
  }
}

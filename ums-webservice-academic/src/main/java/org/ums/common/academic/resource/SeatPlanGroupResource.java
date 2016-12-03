package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by My Pc on 4/21/2016.
 */

@Component
@Path("/academic/seatPlanGroup")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SeatPlanGroupResource extends MutableSeatPlanGroupResource {

  @GET
  @Path("/semester/{semester-id}/type/{type}/update/{update}")
  public JsonObject getSemesterList(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId, final @PathParam("type") int type,
      final @PathParam("update") int update) {

    return mResourceHelper
        .getSeatPlanGroupBySemester(pSemesterId, type, update, pRequest, mUriInfo);
  }

}

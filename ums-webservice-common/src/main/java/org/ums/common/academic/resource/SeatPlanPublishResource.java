package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by My Pc on 8/4/2016.
 */

@Component
@Path("/academic/seatPlanPublish")
public class SeatPlanPublishResource extends MutableSeatPlanPublishResource {

  @GET
  @Path("/semester/{semesterId}")
  public JsonObject getBySemester(final @Context Request pRequest, final @PathParam("semesterId") Integer pSemesterId) throws Exception{
    return mHelper.getBySemester(pSemesterId,pRequest,mUriInfo);
  }
}

package org.ums.common.report.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;

/**
 * Created by My Pc on 5/9/2016.
 */

@Component
@Path("/seatPlanReport")
@Produces({"applciation/pdf"})
public class SeatPlanReport extends Resource {

  @GET
  @Path("/semesterId/{semesterId}/groupNo/{groupNo}/type/{type}")
  public StreamingOutput getSeatPlan(final @Context Request pRequest,
      final @PathParam("semesterId") String pSemesterId,
      final @PathParam("groupNo") String groupNo, final @PathParam("type") String type) {

    return null;
  }

}

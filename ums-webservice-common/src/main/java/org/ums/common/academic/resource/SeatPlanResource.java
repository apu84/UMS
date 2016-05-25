package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.SeatPlanManager;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;

/**
 * Created by My Pc on 5/8/2016.
 */
@Component
@Path("/academic/seatplan")
public class SeatPlanResource extends MutableSeatPlanResource{

  @Autowired
  SeatPlanManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception{
    return mSeatPlanResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/semesterId/{semesterId}/groupNo/{groupNo}/type/{type}")
  @Produces("application/pdf")
  public Response createOrViewSeatPlan(final @Context Request pRequest, final @PathParam("semesterId") String pSemesterId,
                                              final @PathParam("groupNo") String pGroupNo,
                                              final @PathParam("type") String pType)throws Exception{
    StreamingOutput strem =  mSeatPlanResourceHelper.createOrCheckSeatPlanAndReturnRoomList(
        Integer.parseInt(pSemesterId),Integer.parseInt(pGroupNo),Integer.parseInt(pType),pRequest,mUriInfo
    );
    File file = new File( "I:/pdf/seat_plan_report.pdf");
    Response.ResponseBuilder response = Response.ok((Object) file);
    response.header("SeatPlan Report","attachment;filename=seatPlanReport.pdf");
    return  response.build();
  }

}

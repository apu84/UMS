package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.SeatPlanManager;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

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
  public JsonObject createOrViewSeatPlan(final @Context Request pRequest, final @PathParam("semesterId") String pSemesterId,
                                         final @PathParam("groupNo") String pGroupNo,
                                         final @PathParam("type") String pType)throws Exception{
    return mSeatPlanResourceHelper.createOrCheckSeatPlanAndReturnRoomList(
      Integer.parseInt(pSemesterId),Integer.parseInt(pGroupNo),Integer.parseInt(pType),pRequest,mUriInfo
    );
  }

}

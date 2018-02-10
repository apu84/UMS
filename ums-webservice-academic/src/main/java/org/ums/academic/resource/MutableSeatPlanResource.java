package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.SeatPlanResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 5/8/2016.
 */
public class MutableSeatPlanResource extends Resource {

  @Autowired
  SeatPlanResourceHelper mSeatPlanResourceHelper;

  @POST
  public Response createOrViewSeatPlan(final JsonObject pJsonObject) {
    return mSeatPlanResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/updateSeat")
  public Response updateseat(final JsonObject pJsonObject) {

    return mSeatPlanResourceHelper.updateSeatPlan(pJsonObject, mUriInfo);
  }

  @PUT
  public Response updateSeatPlan(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mSeatPlanResourceHelper.put(Long.parseLong(pJsonObject.getString("id")), pRequest, pIfMatchHeader,
        pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSeatPlan(final @PathParam("object-id") Long objectId) throws Exception {
    return mSeatPlanResourceHelper.delete(objectId);
  }
}

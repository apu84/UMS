package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.SeatPlanPublishResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public class MutableSeatPlanPublishResource extends Resource{

  @Autowired
  SeatPlanPublishResourceHelper mHelper;

  @POST
  @Path("/semester/{semester-id}")
  public Response createBySemester(@PathParam("semester-id") Integer pSemesterId, JsonObject pJsonObject) throws  Exception{
    return mHelper.createBySemester(pSemesterId,pJsonObject,mUriInfo);
  }

  @PUT
  @Path("/semester/{semester-id}")
  public Response updateBySemester(@PathParam("semester-id") Integer pSemesterId, JsonObject pJsonObject) throws  Exception{
    return mHelper.updateBySemester(pSemesterId,pJsonObject,mUriInfo);
  }

}

package org.ums.employee.award;

import org.springframework.stereotype.Component;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/award")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AwardInformationResource extends MutableAwardInformationResource {

  @GET
  @Path("/get/employeeId/{employee-id}")
  @UmsLogMessage(message = "Get employee information (award data)")
  public JsonObject getAwardInformation(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("employee-id") String pEmployeeId, final @Context Request pRequest) throws Exception {
    return mAwardInformationResourceHelper.getAwardInformation(pEmployeeId, mUriInfo);
  }
}

package org.ums.employee.training;

import org.springframework.stereotype.Component;
import org.ums.employee.training.MutableTrainingInformationResource;
import org.ums.logs.GetLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/training")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class TrainingInformationResource extends MutableTrainingInformationResource {
  @GET
  @Path("/get/employeeId/{employee-id}")
  @GetLog(message = "Get employee information (training data)")
  public JsonObject getTrainingInformation(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("employee-id") String pEmployeeId, final @Context Request pRequest) throws Exception {
    return mTrainingInformationResourceHelper.getTrainingInformation(pEmployeeId, mUriInfo);
  }
}

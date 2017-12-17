package org.ums.employee.training;

import org.springframework.stereotype.Component;
import org.ums.employee.training.MutableTrainingInformationResource;
import org.ums.resource.Resource;

import javax.json.JsonObject;
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
  public JsonObject getTrainingInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) throws Exception {
    return mTrainingInformationResourceHelper.getTrainingInformation(pEmployeeId, mUriInfo);
  }
}

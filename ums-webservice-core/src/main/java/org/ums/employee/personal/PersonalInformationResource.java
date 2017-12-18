package org.ums.employee.personal;

import org.springframework.stereotype.Component;
import org.ums.employee.personal.MutablePersonalInformationResource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/personal")
public class PersonalInformationResource extends MutablePersonalInformationResource {

  @GET
  @Path("/get/employeeId/{employee-id}")
  public JsonObject getPersonalInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) {
    return mHelper.getPersonalInformation(pEmployeeId, mUriInfo);
  }
}

package org.ums.employee.personal;

import org.springframework.stereotype.Component;
import org.ums.employee.personal.MutablePersonalInformationResource;
import org.ums.logs.UmsLogMessage;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/personal")
public class PersonalInformationResource extends MutablePersonalInformationResource {

  @GET
  @Path("/get/employeeId/{employee-id}")
  @UmsLogMessage(message = "Get employee information (personal data)")
  public JsonObject getPersonalInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) {
    return mHelper.getPersonalInformation(pEmployeeId, mUriInfo);
  }

  @GET
  @Path("/firstName/{first-name}/lastName/{last-name}")
  public JsonObject getSimilarUsers(final @Context Request pRequest, final @PathParam("first-name") String pFirstName,
      final @PathParam("last-name") String pLastName) {
    return mHelper.getSimilarUsers(pFirstName, pLastName, mUriInfo);
  }
}

package org.ums.employee.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/personal")
public class PersonalInformationResource extends MutablePersonalInformationResource {

  @Autowired
  private PersonalInformationResourceHelper mHelper;

  @GET
  @Path("/get/employeeId/{employee-id}")
  @GetLog(message = "Get employee information (personal data)")
  public JsonObject getPersonalInformation(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("employee-id") String pEmployeeId, final @Context Request pRequest) {
    return mHelper.get(pEmployeeId, mUriInfo);
  }

  @GET
  @Path("/firstName/name/{employee-name}")
  @GetLog(message = "Get employee information (get Similar Users)")
  public JsonObject getSimilarUsers(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest,
      final @PathParam("employee-name") String pName) {
    return mHelper.getSimilarUsers(pName, mUriInfo);
  }
}

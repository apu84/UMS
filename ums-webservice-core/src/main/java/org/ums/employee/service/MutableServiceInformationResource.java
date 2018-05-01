package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableServiceInformationResource extends Resource {

  @Autowired
  private ServiceInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  @PostLog(message = "Post employee information (service data)")
  public Response saveEmployeeInformation(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mHelper.saveOrUpdateServiceInformation(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update")
  @PutLog(message = "Update employee information (service data)")
  public Response updateServiceInformation(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return null;
  }
}

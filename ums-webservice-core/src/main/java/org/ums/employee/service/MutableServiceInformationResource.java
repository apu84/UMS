package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableServiceInformationResource extends Resource {

  @Autowired
  private ServiceInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  @UmsLogMessage(message = "Post employee information (service data)")
  public Response saveEmployeeInformation(final JsonObject pJsonObject) {
    return mHelper.saveOrUpdateServiceInformation(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update")
  @UmsLogMessage(message = "Update employee information (service data)")
  public Response updateServiceInformation(final JsonObject pJsonObject) {
    return null;
  }
}

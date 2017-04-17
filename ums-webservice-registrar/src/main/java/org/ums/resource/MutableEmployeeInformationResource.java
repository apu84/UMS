package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.EmployeeInformationResourceHelper;
import org.ums.resource.Resource;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableEmployeeInformationResource extends Resource {

  @Autowired
  EmployeeInformationResourceHelper employeeInformationResourceHelper;

  @POST
  @Path("/saveEmployeeInformation")
  public Response saveEmployeeInformation(final JsonObject pJsonObject) {
    return employeeInformationResourceHelper.saveEmployeeInformation(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/saveServiceInformation")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return employeeInformationResourceHelper.saveEmployeeServiceInformation(pJsonObject, mUriInfo);
  }
}

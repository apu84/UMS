package org.ums.registrar.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.registrar.resource.helper.EmployeeInformationResourceHelper;
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
}

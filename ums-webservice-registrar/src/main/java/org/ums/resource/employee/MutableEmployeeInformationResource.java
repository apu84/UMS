package org.ums.resource.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;
import org.ums.resource.helper.employee.EmployeeInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableEmployeeInformationResource extends Resource {

  @Autowired
  EmployeeInformationResourceHelper mEmployeeInformationResourceHelper;

  @POST
  @Path("/saveEmployeeInformation")
  public Response saveEmployeeInformation(final JsonObject pJsonObject) {
    return mEmployeeInformationResourceHelper.saveEmployeeInformation(pJsonObject, mUriInfo);
  }
}

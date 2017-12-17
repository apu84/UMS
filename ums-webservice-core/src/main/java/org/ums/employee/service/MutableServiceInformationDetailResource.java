package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableServiceInformationDetailResource extends Resource {

  @Autowired
  ServiceInformationDetailResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveEmployeeInformation(final JsonObject pJsonObject) {
    return null;
  }

  @PUT
  @Path("/update")
  public Response updateServiceInformation(final JsonObject pJsonObject) {
    return null;
  }
}

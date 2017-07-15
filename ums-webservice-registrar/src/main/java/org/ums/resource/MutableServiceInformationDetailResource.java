package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.ServiceInformationDetailResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableServiceInformationDetailResource extends Resource {

  @Autowired
  ServiceInformationDetailResourceHelper mHelper;

  @POST
  @Path("/saveServiceInformationDetail")
  public Response saveEmployeeInformation(final JsonObject pJsonObject) {
    return null;
  }

  @PUT
  @Path("/updateServiceInformationDetail")
  public Response updateServiceInformation(final JsonObject pJsonObject) {
    return null;
  }
}

package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.ServiceInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableServiceInformationResource extends Resource {

  @Autowired
  private ServiceInformationResourceHelper mHelper;

  @POST
  @Path("/saveServiceInformation")
  public Response saveEmployeeInformation(final JsonObject pJsonObject) {
    return mHelper.saveOrUpdateServiceInformation(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/updateServiceInformation")
  public Response updateServiceInformation(final JsonObject pJsonObject) {
    return null;
  }
}

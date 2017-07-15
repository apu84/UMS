package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.ServiceInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableServiceInformationResource extends Resource {

  @Autowired
  ServiceInformationResourceHelper serviceInformationResourceHelper;

  // @POST
  // @Path("/saveEmployeeInformation")
  // public Response saveEmployeeInformation(final JsonObject pJsonObject) {
  // return serviceInformationResourceHelper.saveEmployeeInformation(pJsonObject, mUriInfo);
  // }
  //
  // @POST
  // @Path("/saveServiceInformation")
  // public Response saveServiceInformation(final JsonObject pJsonObject) {
  // return serviceInformationResourceHelper.saveEmployeeServiceInformation(pJsonObject, mUriInfo);
  // }
}

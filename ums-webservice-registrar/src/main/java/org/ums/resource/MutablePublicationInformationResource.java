package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.PublicationInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutablePublicationInformationResource extends Resource {

  @Autowired
  PublicationInformationResourceHelper mPublicationInformationResourceHelper;

  @POST
  @Path("/savePublicationInformation")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mPublicationInformationResourceHelper.savePublicationInformation(pJsonObject, mUriInfo);
  }
}

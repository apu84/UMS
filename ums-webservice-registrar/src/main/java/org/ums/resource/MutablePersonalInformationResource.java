package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.PersonalInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutablePersonalInformationResource extends Resource {

  @Autowired
  PersonalInformationResourceHelper mPersonalInformationResourceHelper;

  @POST
  @Path("/savePersonalInformation")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mPersonalInformationResourceHelper.savePersonalInformation(pJsonObject, mUriInfo);
  }
}

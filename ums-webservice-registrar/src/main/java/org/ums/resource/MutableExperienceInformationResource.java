package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.ExperienceInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableExperienceInformationResource extends Resource {

  @Autowired
  ExperienceInformationResourceHelper mExperienceInformationResourceHelper;

  @POST
  @Path("/saveExperienceInformation")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mExperienceInformationResourceHelper.saveExperienceInformation(pJsonObject, mUriInfo);
  }
}

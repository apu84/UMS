package org.ums.employee.experience;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.employee.experience.ExperienceInformationResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableExperienceInformationResource extends Resource {

  @Autowired
  ExperienceInformationResourceHelper mExperienceInformationResourceHelper;

  @POST
  @Path("/save")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mExperienceInformationResourceHelper.saveExperienceInformation(pJsonObject, mUriInfo);
  }
}

package org.ums.employee.experience;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableExperienceInformationResource extends Resource {

  @Autowired
  ExperienceInformationResourceHelper mExperienceInformationResourceHelper;

  @POST
  @Path("/save")
  @UmsLogMessage(message = "Post employee information (Experience data)")
  public Response saveExperienceInformation(@Context HttpServletRequest pHttpServletRequest,
      final JsonObject pJsonObject) {
    return mExperienceInformationResourceHelper.saveExperienceInformation(pJsonObject, mUriInfo);
  }
}

package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.AcademicInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class MutableAcademicInformationResource extends Resource {

  @Autowired
  AcademicInformationResourceHelper mAcademicInformationResourceHelper;

  @POST
  @Path("/saveAcademicInformation")
  public Response saveAcademicInformation(final JsonObject pJsonObject) {
    return mAcademicInformationResourceHelper.saveAcademicInformation(pJsonObject, mUriInfo);
  }
}

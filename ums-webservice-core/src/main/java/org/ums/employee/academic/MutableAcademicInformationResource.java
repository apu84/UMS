package org.ums.employee.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.employee.academic.AcademicInformationResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class MutableAcademicInformationResource extends Resource {

  @Autowired
  AcademicInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveAcademicInformation(final JsonObject pJsonObject) {
    return mHelper.saveAcademicInformation(pJsonObject, mUriInfo);
  }
}

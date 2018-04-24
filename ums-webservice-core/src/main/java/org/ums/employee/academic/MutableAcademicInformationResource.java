package org.ums.employee.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAcademicInformationResource extends Resource {

  @Autowired
  AcademicInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  @UmsLogMessage(message = "Post employee information (academic data)")
  public Response saveAcademicInformation(final JsonObject pJsonObject) {
    return mHelper.saveAcademicInformation(pJsonObject, mUriInfo);
  }
}

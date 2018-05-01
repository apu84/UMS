package org.ums.employee.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PostLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableAcademicInformationResource extends Resource {

  @Autowired
  AcademicInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  @PostLog(message = "Post employee information (academic data)")
  public Response saveAcademicInformation(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mHelper.saveAcademicInformation(pJsonObject, mUriInfo);
  }
}

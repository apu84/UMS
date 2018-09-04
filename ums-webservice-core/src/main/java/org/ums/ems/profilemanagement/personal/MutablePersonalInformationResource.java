package org.ums.ems.profilemanagement.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutablePersonalInformationResource extends Resource {

  @Autowired
  private PersonalInformationResourceHelper mHelper;

  /*
   * @POST
   * 
   * @Path("/save") public Response savePersonalInformation(@Context HttpServletRequest
   * pHttpServletRequest, final JsonObject pJsonObject) { return mHelper.create(pJsonObject,
   * mUriInfo); }
   */

  @PUT
  @Path("/update")
  @PutLog(message = "Updated personal Information")
  public Response updatePersonalInformation(@Context HttpServletRequest pHttpServletRequest,
      final JsonObject pJsonObject) {
    return mHelper.update(pJsonObject, mUriInfo);
  }
}

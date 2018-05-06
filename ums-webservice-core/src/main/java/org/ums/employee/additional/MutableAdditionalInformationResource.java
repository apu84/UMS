package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PostLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableAdditionalInformationResource extends Resource {

  @Autowired
  AdditionalInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  @PostLog(message = "Post employee information (award data)")
  public Response saveAdditionalInformation(@Context HttpServletRequest pHttpServletRequest,
      final JsonObject pJsonObject) {
    return mHelper.saveAdditionalInformation(pJsonObject, mUriInfo);
  }
}

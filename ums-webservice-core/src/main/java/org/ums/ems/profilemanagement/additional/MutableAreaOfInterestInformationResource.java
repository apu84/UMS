package org.ums.ems.profilemanagement.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PostLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableAreaOfInterestInformationResource extends Resource {

  @Autowired
  private AreaOfInterestInformationResourceHelper mHelper;

  @POST
  @PostLog(message = "Created an aoi information")
  public Response create(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject)
      throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}

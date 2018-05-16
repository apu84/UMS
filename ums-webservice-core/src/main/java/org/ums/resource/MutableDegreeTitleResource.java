package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PostLog;
import org.ums.resource.helper.DegreeTitleResourceHelper;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutableDegreeTitleResource extends Resource {

  @Autowired
  private DegreeTitleResourceHelper mHelper;

  @POST
  @PostLog(message = "Created a Degree Title")
  public Response create(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject)
      throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}

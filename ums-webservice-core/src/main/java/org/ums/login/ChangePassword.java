package org.ums.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.login.helper.LoginHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/changePassword")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ChangePassword extends Resource {
  @Autowired
  LoginHelper mLoginHelper;

  @PUT
  public Response changePassword(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) {
    return mLoginHelper.changePassword(pJsonObject);
  }
}

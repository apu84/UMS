package org.ums.common.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.login.helper.LoginHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/forgotPassword")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)

public class ForgotPassword extends Resource {
  @Autowired
  LoginHelper mLoginHelper;

  @POST
  @Path("/resetEmail")
  public Response passwordResetEmailRequest(final @Context Request pRequest,
                                 final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                                 final JsonObject pJsonObject) throws Exception {
    return mLoginHelper.changePassword(pJsonObject);
  }
}

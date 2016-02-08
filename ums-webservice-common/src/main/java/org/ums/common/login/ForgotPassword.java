package org.ums.common.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.login.helper.LoginHelper;
import org.ums.services.LoginService;

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

  @Autowired
  LoginService mLoginService;

  @PUT
  @Path("/passwordReset")
  public Response passwordResetEmailRequest(final @Context Request pRequest,
                                 final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                                 final JsonObject pJsonObject) throws Exception {
    mLoginService.checkAndSendPasswordResetEmailToUser(pJsonObject.getString("userId"));
    return Response.status(Response.Status.NOT_FOUND).build();

  }
}

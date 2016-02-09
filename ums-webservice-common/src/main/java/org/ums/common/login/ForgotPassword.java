package org.ums.common.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.login.helper.LoginHelper;
import org.ums.domain.model.dto.ResponseDto;
import org.ums.services.LoginService;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
  public Response passwordResetEmailRequest(final @Context Request pRequest, final JsonObject pJsonObject) throws Exception {

    ResponseDto response=mLoginService.checkAndSendPasswordResetEmailToUser(pJsonObject.getString("userId"));
    return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();

  }

  @PUT
  @Path("/resetPassword1")
  public Response resetPassword(final @Context Request pRequest, final JsonObject pJsonObject) throws Exception {

    ResponseDto response=mLoginService.resetPassword(pJsonObject.getString("userId"),pJsonObject.getString("passwordResetToken"),pJsonObject.getString("newPassword"),pJsonObject.getString("confirmNewPassword"));
    return Response.ok( response.toString(),MediaType.APPLICATION_JSON).build();

  }

}

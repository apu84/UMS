package org.ums.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;
import org.ums.response.type.GenericResponse;
import org.ums.services.LoginService;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.Map;

@Component
@Path("/forgotPassword")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)

public class ForgotPassword extends Resource {
  @Autowired
  LoginService mLoginService;

  @PUT
  public GenericResponse<Map> passwordResetEmailRequest(final @Context Request pRequest,
                                                        final JsonObject pJsonObject) throws Exception {
    return mLoginService.checkAndSendPasswordResetEmailToUser(pJsonObject.getString("userId"));
  }

  @PUT
  @Path("/resetPassword")
  public GenericResponse<Map> resetPassword(final @Context Request pRequest, final JsonObject pJsonObject) throws Exception {
    return mLoginService.resetPassword(pJsonObject.getString("userId"),
        pJsonObject.getString("passwordResetToken"),
        pJsonObject.getString("newPassword"),
        pJsonObject.getString("confirmNewPassword"));
  }
}

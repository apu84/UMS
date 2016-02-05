package org.ums.common.login.helper;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.regular.User;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

@Component
public class LoginHelper {
  @Autowired
  @Qualifier("jdbcRealm")
  AuthorizingRealm mAuthenticationRealm;
  @Autowired
  @Qualifier("userManager")
  private ContentManager<User, MutableUser, String> mUserManager;
  @Autowired
  private PasswordService mPasswordService;

  public Response changePassword(final JsonObject pJsonObject) throws Exception {

    Subject loggedInUser = SecurityUtils.getSubject();
    String userName = loggedInUser.getPrincipal().toString();
    User currentUser = mUserManager.get(userName);

    String currentPassword = pJsonObject.getString("currentPassword");
    String newPassword = pJsonObject.getString("newPassword");
    String confirmNewPassword = pJsonObject.getString("confirmNewPassword");

    if (StringUtils.isEmpty(currentPassword)
        || StringUtils.isEmpty(newPassword)
        || StringUtils.isEmpty(confirmNewPassword)
        || !newPassword.equals(confirmNewPassword)) {
      return Response.notModified().build();
    }
    if (currentUser.getTemporaryPassword() != null) {
      if (String.valueOf(String.valueOf(currentUser.getTemporaryPassword())).equals(currentPassword)) {
        changePassword(currentUser, newPassword);
        return Response.ok().build();
      }
    } else if (mPasswordService.passwordsMatch(currentPassword, String.valueOf(currentUser.getPassword()))) {
      changePassword(currentUser, newPassword);
//      mAuthenticationRealm.getAuthenticationCache().remove(SecurityUtils.getSubject().getPrincipal());
      return Response.ok().build();
    }

    return Response.notModified().build();
  }

  public Response forgotPassword(final JsonObject pJsonObject) throws Exception {
    String userId = pJsonObject.getString("userId");
    User user = mUserManager.get(userId);
    if(user==null){
      //Set proper message with 200 response code and KO status . STATUS_CODE,STATUS_MESSAGE,RESPONSE TYPE 200;
      //return;
    }

    //
    return null;
    }

  protected void changePassword(User pCurrentUser, final String pNewPassword) throws Exception {
    MutableUser mutableUser = pCurrentUser.edit();
    mutableUser.setPassword(mPasswordService.encryptPassword(pNewPassword).toCharArray());
    mutableUser.setTemporaryPassword(null);
    mutableUser.commit(true);
  }
}

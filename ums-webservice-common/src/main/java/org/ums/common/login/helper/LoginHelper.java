package org.ums.common.login.helper;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.domain.model.MutableUser;
import org.ums.domain.model.User;
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

    if (mPasswordService.passwordsMatch(currentPassword, String.valueOf(currentUser.getPassword()))) {
      MutableUser mutableUser = currentUser.edit();
      mutableUser.setPassword(mPasswordService.encryptPassword(newPassword).toCharArray());
      mutableUser.setTemporaryPassword(null);
      mutableUser.commit(true);
//      mAuthenticationRealm.getAuthenticationCache().remove(SecurityUtils.getSubject().getPrincipal());
      return Response.ok().build();
    }

    return Response.notModified().build();
  }
}

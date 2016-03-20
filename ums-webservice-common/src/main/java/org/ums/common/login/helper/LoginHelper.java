package org.ums.common.login.helper;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.manager.UserManager;
import org.ums.persistent.model.PersistentBearerAccessToken;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Component
public class LoginHelper {
  @Autowired
  AuthorizingRealm mAuthenticationRealm;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private PasswordService mPasswordService;
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;

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
        String newToken = changePassword(currentUser, newPassword);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("token", newToken);
        return Response.ok(builder.build()).build();
      }
    } else if (mPasswordService.passwordsMatch(currentPassword, String.valueOf(currentUser.getPassword()))) {
      String newToken = changePassword(currentUser, newPassword);
//      mAuthenticationRealm.getAuthenticationCache().remove(SecurityUtils.getSubject().getPrincipal());
      JsonObjectBuilder builder = Json.createObjectBuilder();
      builder.add("token", newToken);
      return Response.ok(builder.build()).build();
    }

    return Response.notModified().build();
  }

  @Transactional
  protected String changePassword(User pCurrentUser, final String pNewPassword) throws Exception {
    MutableUser mutableUser = pCurrentUser.edit();
    mutableUser.setPassword(mPasswordService.encryptPassword(pNewPassword).toCharArray());
    mutableUser.setTemporaryPassword(null);
    mutableUser.commit(true);

    mBearerAccessTokenManager.invalidateTokens(pCurrentUser.getId());

    String newToken = UUID.randomUUID().toString();
    MutableBearerAccessToken accessToken = new PersistentBearerAccessToken();
    accessToken.setUserId(pCurrentUser.getId());
    accessToken.setId(newToken);
    accessToken.commit(false);

    return newToken;
  }
}

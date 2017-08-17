package org.ums.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.persistent.model.PersistentBearerAccessToken;
import org.ums.token.TokenBuilder;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class ChangePassword extends PathMatchingFilter {
  private static final Logger mLogger = LoggerFactory.getLogger(ChangePassword.class);

  @Autowired
  UserManager mUserManager;

  @Autowired
  PasswordService mPasswordService;

  @Autowired
  BearerAccessTokenManager mBearerAccessTokenManager;

  @Autowired
  TokenBuilder mTokenBuilder;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    String content = IOUtils.toString(request.getInputStream());
    JSONParser parser = new JSONParser();
    JSONObject requestJson = (JSONObject) parser.parse(content);

    String currentPassword = requestJson.get("currentPassword").toString();
    String newPassword = requestJson.get("newPassword").toString();
    String confirmNewPassword = requestJson.get("confirmNewPassword").toString();

    if(StringUtils.isEmpty(currentPassword) || StringUtils.isEmpty(newPassword)
        || StringUtils.isEmpty(confirmNewPassword) || !newPassword.equals(confirmNewPassword)) {
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
      return false;
    }

    Subject loggedInUser = SecurityUtils.getSubject();
    String userName = loggedInUser.getPrincipal().toString();
    User currentUser = mUserManager.get(userName);

    if(currentUser.getTemporaryPassword() != null) {
      if(String.valueOf(String.valueOf(currentUser.getTemporaryPassword())).equals(currentPassword)) {
        return responseNewToken(changePassword(currentUser, newPassword), response);
      }
    }
    else if(mPasswordService.passwordsMatch(currentPassword, String.valueOf(currentUser.getPassword()))) {
      return responseNewToken(changePassword(currentUser, newPassword), response);
    }
    return super.preHandle(request, response);
  }

  @Transactional
  public BearerAccessToken changePassword(User pCurrentUser, final String pNewPassword) {
    MutableUser mutableUser = pCurrentUser.edit();
    mutableUser.setPassword(mPasswordService.encryptPassword(pNewPassword).toCharArray());
    mutableUser.setTemporaryPassword(null);
    mutableUser.update();

    mBearerAccessTokenManager.getByUser(pCurrentUser.getId()).forEach((token) -> token.edit().delete());

    MutableBearerAccessToken newToken = new PersistentBearerAccessToken();
    newToken.setUserId(pCurrentUser.getId());
    newToken.setId(mTokenBuilder.accessToken());
    newToken.setRefreshToken(mTokenBuilder.refreshToken());
    newToken.create();

    return newToken;
  }

  private boolean responseNewToken(BearerAccessToken newToken, ServletResponse pResponse) throws IOException {
    pResponse.setContentType("application/json");
    PrintWriter out = pResponse.getWriter();
    out.print(String.format("{\"token\": \"Bearer %s\", \"refreshToken\": \"Bearer %s\"}", newToken.getId(),
        newToken.getRefreshToken()));
    out.flush();
    return false;
  }
}

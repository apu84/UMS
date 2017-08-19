package org.ums.filter;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChangePassword extends AbstractPathMatchingFilter {
  private static final Logger mLogger = LoggerFactory.getLogger(ChangePassword.class);

  @Autowired
  UserManager mUserManager;

  @Autowired
  PasswordService mPasswordService;

  @Autowired
  BearerAccessTokenManager mBearerAccessTokenManager;

  @Autowired
  TokenBuilder mTokenBuilder;

  @Autowired
  @Qualifier("plainPasswordMatcher")
  CredentialsMatcher mPlainPasswordMatcher;

  @Autowired
  @Qualifier("credentialsMatcher")
  CredentialsMatcher mCredentialsMatcher;

  private String mSalt;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    JSONObject requestJson = getRequestJson((HttpServletRequest) request);

    String currentPassword = requestJson.get("currentPassword").toString();
    String newPassword = requestJson.get("newPassword").toString();
    String confirmNewPassword = requestJson.get("confirmNewPassword").toString();

    if(StringUtils.isEmpty(currentPassword) || StringUtils.isEmpty(newPassword)
        || StringUtils.isEmpty(confirmNewPassword) || !newPassword.equals(confirmNewPassword)) {
      return sendError("Confirm password doesn't match", response);
    }

    User currentUser = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    boolean newUser = currentUser.getTemporaryPassword() != null;
    AuthenticationToken token = new UsernamePasswordToken(currentUser.getId(), currentPassword);;
    AuthenticationInfo authenticationInfo;

    if(newUser) {
      authenticationInfo =
          new SimpleAuthenticationInfo(currentUser.getId(), currentUser.getTemporaryPassword(), "newUserRealm");
    }
    else {
      authenticationInfo =
          new SimpleAuthenticationInfo(currentUser.getId(), currentUser.getPassword(), ByteSource.Util.bytes(mSalt),
              "userRealm");
    }

    if(getCredentialsMatcher(newUser).doCredentialsMatch(token, authenticationInfo)) {
      return responseNewToken(changePassword(currentUser, newPassword), response);
    }
    else {
      return sendError("Current password doesn't match", response);
    }
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
    out.print(String.format("{\"accessToken\": \"Bearer %s\", \"refreshToken\": \"Bearer %s\"}", newToken.getId(),
        newToken.getRefreshToken()));
    out.flush();
    return false;
  }

  private CredentialsMatcher getCredentialsMatcher(boolean newUser) {
    return newUser ? mPlainPasswordMatcher : mCredentialsMatcher;
  }

  public void setSalt(String pSalt) {
    mSalt = pSalt;
  }
}

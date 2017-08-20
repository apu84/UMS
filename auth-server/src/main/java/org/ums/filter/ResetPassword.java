package org.ums.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.shiro.authc.credential.PasswordService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.message.MessageResource;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class ResetPassword extends AbstractPathMatchingFilter {
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private MessageResource mMessageResource;
  @Autowired
  private PasswordService mPasswordService;

  private String mSigningKey;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    JSONObject requestJson = getRequestJson((HttpServletRequest) request);
    Validate.notNull(requestJson.get("passwordResetToken"), "Empty password reset token");
    Validate.notNull(requestJson.get("newPassword"), "Empty new password");
    Validate.notNull(requestJson.get("confirmNewPassword"), "Empty confirm new password");
    Validate.isTrue(requestJson.get("newPassword").toString().equals(requestJson.get("confirmNewPassword").toString()));
    resetPassword(requestJson.get("passwordResetToken").toString(), requestJson.get("newPassword").toString()
        .toCharArray(), (HttpServletResponse) response);
    return false;
  }

  private void resetPassword(String prToken, char[] password, HttpServletResponse pResponse) throws IOException {
    if(isValidToken(prToken)) {
      Optional<String> userId = getUser(prToken);
      if(userId.isPresent()) {
        User user = mUserManager.get(userId.get());
        MutableUser mutableUser = user.edit();
        mutableUser.setPassword(mPasswordService.encryptPassword(password).toCharArray());
        mutableUser.setPasswordResetToken(null);
        mutableUser.setPasswordTokenGenerateDateTime(null);
        mutableUser.update();

        sendSuccess(String.format("{\"userId\":\"%s\"}", userId.get()), pResponse);
      }
    }
    else {
      sendError(mMessageResource.getMessage("invalid.password.reset.url"), pResponse);
    }
  }

  private boolean isValidToken(final String token) {
    boolean valid = true;
    String userId = null;
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
      userId = claimsJws.getBody().getSubject();
    } catch(JwtException jwte) {
      valid = false;
    }
    if(valid) {
      if(!mUserManager.exists(userId)) {
        return false;
      }
      User user = mUserManager.get(userId);
      if(!user.getPasswordResetToken().equals(token)) {
        return false;
      }
    }
    return valid;
  }

  private Optional<String> getUser(final String token) {
    String user = null;
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
      user = claimsJws.getBody().getSubject();
    } catch(JwtException jwte) {

    }
    return StringUtils.isEmpty(user) ? Optional.empty() : Optional.of(user);
  }

  public String getSigningKey() {
    return mSigningKey;
  }

  public void setSigningKey(String pSigningKey) {
    mSigningKey = pSigningKey;
  }
}

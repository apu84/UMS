package org.ums.authentication;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.PasswordMatcher;

public class PlainPasswordMatcher extends PasswordMatcher {
  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    Object submittedPassword = getSubmittedPassword(token);
    Object storedCredentials = getStoredPassword(info);

    return storedCredentials.toString().equals(submittedPassword.toString());
  }
}

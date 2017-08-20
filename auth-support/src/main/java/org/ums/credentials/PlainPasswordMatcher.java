package org.ums.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.PasswordMatcher;

public class PlainPasswordMatcher extends PasswordMatcher {
  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    char[] submittedPassword = (char[]) getSubmittedPassword(token);
    Object storedCredentials = getStoredPassword(info);

    return storedCredentials.toString().equals(String.valueOf(submittedPassword));
  }
}

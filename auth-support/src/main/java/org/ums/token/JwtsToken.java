package org.ums.token;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtsToken implements AuthenticationToken {
  private final String mUserName;
  private final String mToken;
  private final boolean rememberMe = false;

  public JwtsToken(String pUserName, String pToken) {
    mUserName = pUserName;
    mToken = pToken;
  }

  @Override
  public String getPrincipal() {
    return mUserName;
  }

  @Override
  public String getCredentials() {
    return mToken;
  }
}

package org.ums.security.bearertoken;

import org.apache.shiro.authc.AuthenticationToken;

public class BearerToken implements AuthenticationToken {

  private final String userId;
  private final String token;

  public BearerToken(String userId, String token) {
    this.userId = userId;
    this.token = token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return userId;
  }

}

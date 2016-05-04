package org.ums.security.bearertoken;

import org.apache.shiro.authc.AuthenticationToken;

public class BearerToken implements AuthenticationToken {

  private final String userId;
  private final String token;
  private final String path;

  public BearerToken(String userId, String token, String path) {
    this.userId = userId;
    this.token = token;
    this.path = path;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return userId;
  }

  public String getPath() {
    return path;
  }
}

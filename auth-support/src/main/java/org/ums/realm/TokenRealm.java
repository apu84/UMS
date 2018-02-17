package org.ums.realm;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.token.JwtsToken;
import org.ums.usermanagement.permission.UserRolePermissions;
import org.ums.usermanagement.role.Role;

import io.jsonwebtoken.*;

public class TokenRealm extends AuthorizingRealm {
  private static final Logger mLogger = LoggerFactory.getLogger(TokenRealm.class);
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;
  @Autowired
  private UserRolePermissions mUserRolePermissions;

  private String mSigningKey;

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtsToken;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken pAuthenticationToken)
      throws AuthenticationException {
    JwtsToken token = (JwtsToken) pAuthenticationToken;
    String userId = token.getPrincipal();
    String credentials = token.getCredentials();

    Validate.notNull(userId, "UserId can't be null");
    Validate.notNull(token, "Token can't be null");
    BearerAccessToken dbToken;
    List<BearerAccessToken> dbTokens = mBearerAccessTokenManager.getByAccessToken(credentials);
    if(dbTokens.size() == 0) {
      mLogger.error("User: " + userId + " token: " + credentials + " is invalid");
      throw new AuthenticationException("Not able to find provided bearer access token");
    }
    else {
      dbToken = dbTokens.get(0);
    }

    if(tokenIsInvalid(token, dbToken)) {
      mLogger.error("User: " + token.getPrincipal() + ", Invalid access token: " + token.getCredentials());
      throw new AuthenticationException("Access denied. Invalid access token");
    }

    return new SimpleAuthenticationInfo(userId, credentials, getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException {
    String userId = (String) getAvailablePrincipal(principals);
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(
        mUserRolePermissions.getUserRoles(userId).stream().map(Role::getName).collect(Collectors.toSet()));
    info.setStringPermissions(mUserRolePermissions.getUserRolePermissions(userId));
    return info;
  }

  private String getUserName(final String jwt) throws ExpiredJwtException {
    Jws<Claims> claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(jwt);
    return claims.getBody().getSubject();
  }

  private boolean isExpiredToken(final String jwt) {
    boolean isExpired = false;
    try {
      getUserName(jwt);
    } catch(JwtException jwte) {
      isExpired = true;
    }
    return isExpired;
  }

  private boolean tokenIsInvalid(JwtsToken token, BearerAccessToken dbToken) {
    return token == null || dbToken == null || isExpiredToken(token.getCredentials())
        || !dbToken.getUserId().equals(token.getPrincipal());
  }

  public void setSigningKey(String pSigningKey) {
    mSigningKey = pSigningKey;
  }

  public String getSigningKey() {
    return mSigningKey;
  }
}

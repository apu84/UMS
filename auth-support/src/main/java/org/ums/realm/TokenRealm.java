package org.ums.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.ums.token.TokenBuilder;
import org.ums.usermanagement.permission.UserRolePermissions;
import org.ums.usermanagement.role.Role;

public class TokenRealm extends AuthorizingRealm {
  private static final Logger mLogger = LoggerFactory.getLogger(TokenRealm.class);
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;
  @Autowired
  private UserRolePermissions mUserRolePermissions;

  private TokenBuilder mTokenBuilder;

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
    Set<String> roles = new HashSet<>();
    for(Role role : mUserRolePermissions.getUserRoles(userId)) {
      if(role != null) {
        roles.add(role.getName());
      }
    }
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    Set<String> rolePermissions = mUserRolePermissions.getUserRolePermissions(userId);
    rolePermissions.addAll(mUserRolePermissions.getUserAdditionalPermissions(userId));
    info.setStringPermissions(rolePermissions);
    return info;
  }

  private boolean tokenIsInvalid(JwtsToken token, BearerAccessToken dbToken) {
    return token == null || dbToken == null || !mTokenBuilder.isValidToken(token.getCredentials())
        || !dbToken.getUserId().equals(token.getPrincipal());
  }

  public void setTokenBuilder(TokenBuilder pTokenBuilder) {
    mTokenBuilder = pTokenBuilder;
  }
}

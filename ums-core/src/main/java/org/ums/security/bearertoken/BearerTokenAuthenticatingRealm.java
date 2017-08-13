package org.ums.security.bearertoken;

import java.util.*;

import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.manager.ContentManager;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.permission.PermissionManager;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

import com.google.common.collect.Sets;

public class BearerTokenAuthenticatingRealm extends AuthorizingRealm {
  private static final Logger mLogger = LoggerFactory.getLogger(BearerTokenAuthenticatingRealm.class);

  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;
  @Autowired
  private PermissionManager mPermissionManager;
  @Autowired
  private ContentManager<User, MutableUser, String> mUserManager;
  @Autowired
  private Integer sessionTimeout = 15;
  @Autowired
  private Integer sessionTimeoutInterval = 1;
  @Autowired
  private String mLogoutUri = "/logout";

  public void setLogoutUri(String pLogoutUri) {
    mLogoutUri = pLogoutUri;
  }

  private class BearerAuthenticationInfo implements AuthenticationInfo {
    private final BearerAccessToken token;

    BearerAuthenticationInfo(BearerAccessToken token) {
      this.token = token;
    }

    @Override
    public Object getCredentials() {
      return token.getId();
    }

    @Override
    public PrincipalCollection getPrincipals() {
      RealmSecurityManager manager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
      SimplePrincipalCollection ret = new SimplePrincipalCollection();
      for(Realm realm : manager.getRealms()) {
        if(realm instanceof ProfileRealm) {
          String userId = token.getUserId();
          if(((ProfileRealm) realm).accountExists(userId)) {
            ret.add(userId, realm.getName());
          }
        }
      }
      ret.add(token.getId(), getName());
      return ret;
    }
  }

  public BearerTokenAuthenticatingRealm() {
    super(new AllowAllCredentialsMatcher());
    setAuthenticationTokenClass(BearerToken.class);
  }

  private static boolean tokenIsInvalid(BearerToken token, BearerAccessToken dbToken) {
    return token == null || dbToken == null || !dbToken.getUserId().equals(token.getPrincipal());
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken pAuthenticationToken)
      throws AuthenticationException {
    BearerToken token = (BearerToken) pAuthenticationToken;
    String userId = (String) token.getPrincipal();
    String credentials = (String) token.getCredentials();

    Validate.notNull(userId, "UserId can't be null");
    Validate.notNull(token, "Token can't be null");
    BearerAccessToken dbToken;

    try {
      dbToken = mBearerAccessTokenManager.get(credentials);
    } catch(Exception e) {
      mLogger.error("User: " + userId + " Credential: " + credentials + " is invalid");
      throw new AuthenticationException("Not able to find provided bearer access token", e);
    }

    if(tokenIsInvalid(token, dbToken)) {
      mLogger.error("User: " + token.getPrincipal() + ", Invalid access token: " + token.getCredentials());
      throw new AuthenticationException("Access denied. Invalid access token");
    }

    /**
     * If difference between current time and last accessed time is less than 15 minutes or less
     * than some pre-configured time that update last access time. Otherwise throw an SessionTimeout
     * Exception.
     */
    // Bypassing logout uri.
    if(!token.getPath().contains(mLogoutUri)) {
      Date currentDate = new Date();
      long diff = currentDate.getTime() - dbToken.getLastAccessTime().getTime();
      long diffMinutes = diff / (60 * 1000);

      if(diffMinutes >= sessionTimeoutInterval && diffMinutes <= sessionTimeout) {
        MutableBearerAccessToken mutableBearerAccessToken = dbToken.edit();
        mutableBearerAccessToken.update();
      }
      else if(diffMinutes > sessionTimeout) {
        if(mLogger.isDebugEnabled()) {
          mLogger.debug("Expired access token: " + dbToken.getId());
        }
        throw new AuthenticationException("Expired token");
      }
    }

    return new BearerAuthenticationInfo(dbToken);
  }

  // TODO: Move this method to a common place so both the realm can use same authorization base
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException {
    // null usernames are invalid
    if(principals == null) {
      throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
    }
    SimpleAuthorizationInfo info = null;
    String token = (String) getAvailablePrincipal(principals);
    try {
      BearerAccessToken bearerAccessToken = mBearerAccessTokenManager.get(token);
      User user = mUserManager.get(bearerAccessToken.getUserId());
      info = new SimpleAuthorizationInfo(Sets.newHashSet(user.getPrimaryRole().getName()));
      List<Permission> rolePermissions = mPermissionManager.getPermissionByRole(user.getPrimaryRole());

      Set<String> permissions = new HashSet<>();

      for(Permission permission : rolePermissions) {
        permissions.addAll(permission.getPermissions());
      }

      info.setStringPermissions(permissions);
    } catch(Exception e) {
      throw new AuthorizationException("Invalid access token", e);
    }
    return info;
  }

  @Override
  public void onLogout(PrincipalCollection principals) {
    super.onLogout(principals);
    deleteTokens(principals);
  }

  @SuppressWarnings("unchecked")
  private void deleteTokens(PrincipalCollection principals) {
    Collection<String> tokens = principals.fromRealm(getName());
    if(tokens != null) {
      for(String token : tokens) {
        try {
          mBearerAccessTokenManager.delete(mBearerAccessTokenManager.get(token).edit());
        } catch(Exception e) {
          mLogger.error("Failed to delete token: " + token, e);
        }

      }
    }
  }

}

package org.ums.security.bearertoken;

import com.google.common.collect.Sets;
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
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.manager.ContentManager;
import org.ums.manager.PermissionManager;

import java.util.*;

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
      try {
        for (Realm realm : manager.getRealms()) {
          if (realm instanceof ProfileRealm) {
            String userId = token.getUserId();
            if (((ProfileRealm) realm).accountExists(userId)) {
              ret.add(userId, realm.getName());
            }
          }
        }
        ret.add(token.getId(), getName());
      } catch (Exception e) {
        e.printStackTrace();
      }

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
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken pAuthenticationToken) throws AuthenticationException, ExpiredSessionException {
    BearerToken token = (BearerToken) pAuthenticationToken;
    String userId = (String) token.getPrincipal();
    String credentials = (String) token.getCredentials();

    Validate.notNull(userId, "UserId can't be null");
    Validate.notNull(token, "Token can't be null");
    BearerAccessToken dbToken;
    try {
      dbToken = mBearerAccessTokenManager.get(credentials);
      if (tokenIsInvalid(token, dbToken)) {
        throw new AuthenticationException("Access denied. Invalid access token");
      }

      /**
       * If difference between current time and last accessed time is less than 15 minutes
       * or less than some pre-configured time that update last access time. Otherwise throw
       * an SessionTimeout Exception.
       */
      Date currentDate = new Date();
      long diff = currentDate.getTime() - dbToken.getLastAccessTime().getTime();
      long diffMinutes = diff / (60 * 1000) % 60;

      if (diffMinutes >= sessionTimeoutInterval && diffMinutes <= sessionTimeout) {
        MutableBearerAccessToken mutableBearerAccessToken = dbToken.edit();
        mutableBearerAccessToken.commit(true);
      } else if (diffMinutes > sessionTimeout) {
        MutableBearerAccessToken mutableBearerAccessToken = dbToken.edit();
        mutableBearerAccessToken.delete();
        if (mLogger.isDebugEnabled()) {
          mLogger.debug("Removed expired access token: " + dbToken.getId());
        }
        throw new ExpiredSessionException("Access denied, Access token is expired");
      }

    } catch (Exception e) {
      throw new AuthenticationException("Not able to find provided bearer access token");
    }
    if (mLogger.isDebugEnabled()) {
      mLogger.debug("Authorized user: " + userId);
    }
    return new BearerAuthenticationInfo(dbToken);
  }

  //TODO: Move this method to a common place so both the realm can use same authorization base
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException {
    //null usernames are invalid
    if (principals == null) {
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

      for (Permission permission : rolePermissions) {
        permissions.addAll(permission.getPermissions());
      }

      info.setStringPermissions(permissions);
    } catch (Exception e) {
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
    if (tokens != null) {
      for (String token : tokens) {
        mBearerAccessTokenManager.deleteToken(token);
      }
    }
  }

}

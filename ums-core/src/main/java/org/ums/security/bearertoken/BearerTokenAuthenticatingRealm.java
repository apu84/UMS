package org.ums.security.bearertoken;

import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;

import java.util.Collection;

public class BearerTokenAuthenticatingRealm extends AuthenticatingRealm {
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;

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
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken pAuthenticationToken) throws AuthenticationException {
    BearerToken token = (BearerToken) pAuthenticationToken;
    String userId = (String) token.getPrincipal();
    String credentials = (String) token.getCredentials();

    Validate.notNull(userId, "Email can't be null");
    Validate.notNull(token, "Token can't be null");
    BearerAccessToken dbToken;
    try {
      dbToken = mBearerAccessTokenManager.get(credentials);
      if (tokenIsInvalid(token, dbToken)) {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    return new BearerAuthenticationInfo(dbToken);
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

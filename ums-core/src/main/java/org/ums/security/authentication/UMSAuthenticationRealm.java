package org.ums.security.authentication;

import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.User;
import org.ums.manager.PermissionManager;
import org.ums.manager.UserManager;
import org.ums.security.bearertoken.ProfileRealm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UMSAuthenticationRealm extends JdbcRealm implements ProfileRealm {
  private static final Logger mLogger = LoggerFactory.getLogger(UMSAuthenticationRealm.class);
  private String mSalt;
  private PasswordMatcher mPlainPasswordMatcher;
  private CredentialsMatcher mHashCredentialsMatcher;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private PermissionManager mPermissionManager;
  @Autowired
  private UMSConfiguration mUMSConfiguration;

  private static final String LOGIN_AS_SEPARATOR = ">";

  public UMSAuthenticationRealm() {
    setAuthenticationTokenClass(UsernamePasswordToken.class);
  }

  public void setSalt(String pSalt) {
    mSalt = pSalt;
  }

  @Override
  protected String getSaltForUser(String username) {
    return mSalt;
  }

  public PasswordMatcher getPlainPasswordMatcher() {
    return mPlainPasswordMatcher;
  }

  public void setPlainPasswordMatcher(PasswordMatcher pPlainPasswordMatcher) {
    mPlainPasswordMatcher = pPlainPasswordMatcher;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

    SimpleAuthenticationInfo info;

    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String userId = upToken.getUsername();

    // Null username is invalid
    if(StringUtils.isEmpty(userId)) {
      throw new AccountException("Null username is not allowed by this realm.");
    }

    try {
      User adminUser = null;
      if(userId.contains(LOGIN_AS_SEPARATOR)) {
        userId = userId.split(LOGIN_AS_SEPARATOR)[1];
        adminUser = mUserManager.get(mUMSConfiguration.getAdminUser());
      }

      setCredentialsMatcher(mHashCredentialsMatcher);

      User user = mUserManager.get(userId);

      if(user.getPassword() == null) {

        if(user.getTemporaryPassword() == null) {
          throw new UnknownAccountException("No account found for user [" + userId + "]");
        }
        else {
          setCredentialsMatcher(mPlainPasswordMatcher);
          info = new SimpleAuthenticationInfo(userId, user.getTemporaryPassword(), getName());
        }

      }
      else {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        if(adminUser != null) {
          userIds.add(adminUser.getId());
          info = new SimpleAuthenticationInfo(userIds, adminUser.getPassword(), getName());
        }
        else {
          info = new SimpleAuthenticationInfo(userIds, user.getPassword(), getName());
        }

        if(mSalt != null) {
          info.setCredentialsSalt(ByteSource.Util.bytes(mSalt));
        }
      }

    } catch(Exception e) {
      mLogger.error("Exception while trying to login ", e);
      throw new AuthenticationException(e);
    }

    return info;
  }

  public void setHashCredentialsMatcher(CredentialsMatcher pHashCredentialsMatcher) {
    mHashCredentialsMatcher = pHashCredentialsMatcher;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException {
    // null usernames are invalid
    if(principals == null) {
      throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
    }
    SimpleAuthorizationInfo info = null;
    String username = (String) getAvailablePrincipal(principals);
    try {
      User user = mUserManager.get(username);
      info = new SimpleAuthorizationInfo(Sets.newHashSet(user.getPrimaryRole().getName()));
      List<Permission> rolePermissions = mPermissionManager.getPermissionByRole(user.getPrimaryRole());

      Set<String> permissions = new HashSet<>();

      for(Permission permission : rolePermissions) {
        permissions.addAll(permission.getPermissions());
      }

      info.setStringPermissions(permissions);
    } catch(Exception e) {
      throw new AuthorizationException(e);
    }
    return info;

  }

  @Override
  public boolean accountExists(String pUserId) {
    return !StringUtils.isEmpty(pUserId) && mUserManager.get(pUserId) != null;
  }

  @Override
  public User getAccount(String pUserId) {
    return mUserManager.get(pUserId);
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token != null && getAuthenticationTokenClass().isAssignableFrom(token.getClass());
  }
}

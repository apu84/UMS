package org.ums.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class UserRealm extends AuthorizingRealm {
  protected static final String LOGIN_AS_SEPARATOR = ">";
  @Autowired
  private UserManager mUserManager;
  private String mSalt;

  public String getSalt() {
    return mSalt;
  }

  public void setSalt(String pSalt) {
    mSalt = pSalt;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return new SimpleAuthorizationInfo();
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String userId = (String) token.getPrincipal();
    User user = getUser(userId);
    return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), ByteSource.Util.bytes(mSalt), getName());
  }

  User getUser(String pUserId) throws UnknownAccountException {
    User user = mUserManager.get(pUserId);
    if(user == null) {
      throw new UnknownAccountException();
    }
    return user;
  }
}

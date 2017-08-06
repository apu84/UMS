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
    String username = (String) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    return authorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String username = (String) token.getPrincipal();
    User user = mUserManager.get(username);
    if(user == null) {
      throw new UnknownAccountException();
    }
    return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), ByteSource.Util.bytes(mSalt), getName());
  }
}

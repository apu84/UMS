package org.ums.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.configuration.UMSConfiguration;
import org.ums.usermanagement.user.User;

import com.google.common.collect.Lists;

public class AdminUserRealm extends UserRealm {
  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Override
  public boolean supports(AuthenticationToken token) {
    return super.supports(token) && token.getPrincipal().toString().contains(LOGIN_AS_SEPARATOR);
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String userId = token.getPrincipal().toString().split(LOGIN_AS_SEPARATOR)[1];
    User user = getUser(userId);
    User adminUser = getUser(mUMSConfiguration.getAdminUser());
    return new SimpleAuthenticationInfo(Lists.newArrayList(user.getId(), adminUser.getId()), adminUser.getPassword(),
        ByteSource.Util.bytes(getSalt()), getName());
  }
}

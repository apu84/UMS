package org.ums.dummy.shared.authentication;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.dummy.shared.dao.UserDao;
import org.ums.dummy.shared.model.User;

@Component
public class DummyRealm extends JdbcRealm {
  @Autowired
  UserDao userDao;

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // identify account to log to
    UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
    final String username = userPassToken.getUsername();

    if (username == null) {
      throw new AuthenticationException("User name is empty");
    }

    // read password hash and salt from db
    final User user = userDao.getByEmail(username);

    if (user == null) {
      throw new AuthenticationException("Can not find user");
    }

    // return salted credentials
    return new DummySaltedAuthenticationInfo(username, user.getPassword(), user.getSalt());
  }

}

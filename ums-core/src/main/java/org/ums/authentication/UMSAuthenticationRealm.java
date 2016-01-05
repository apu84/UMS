package org.ums.authentication;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.domain.model.MutableUser;
import org.ums.domain.model.User;
import org.ums.manager.ContentManager;


public class UMSAuthenticationRealm extends JdbcRealm {
  private String mSalt;
  private PasswordMatcher mPlainPasswordMatcher;
  private CredentialsMatcher mHashCredentialsMatcher;
  @Autowired
  @Qualifier("userManager")
  private ContentManager<User, MutableUser, String> mUserManager;

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
    String username = upToken.getUsername();

    // Null username is invalid
    if (username == null) {
      throw new AccountException("Null usernames are not allowed by this realm.");
    }

    try {
      setCredentialsMatcher(mHashCredentialsMatcher);

      User user = mUserManager.get(username);

      if (user.getPassword() == null) {

        if (user.getTemporaryPassword() == null) {
          throw new UnknownAccountException("No account found for user [" + username + "]");
        } else {
          setCredentialsMatcher(mPlainPasswordMatcher);
          info = new SimpleAuthenticationInfo(username, user.getTemporaryPassword(), getName());
        }

      } else {
        info = new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        if (mSalt != null) {
          info.setCredentialsSalt(ByteSource.Util.bytes(mSalt));
        }
      }

    } catch (Exception e) {
      throw new AuthenticationException(e);
    }

    return info;
  }

  public void setHashCredentialsMatcher(CredentialsMatcher pHashCredentialsMatcher) {
    mHashCredentialsMatcher = pHashCredentialsMatcher;
  }
  //TODO: Implement AuthorizationInfo and Permissions also
}

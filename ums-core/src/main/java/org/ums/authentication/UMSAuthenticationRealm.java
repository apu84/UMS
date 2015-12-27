package org.ums.authentication;

import org.apache.shiro.realm.jdbc.JdbcRealm;


public class UMSAuthenticationRealm extends JdbcRealm {
  private String mSalt;

  public void setSalt(String pSalt) {
    mSalt = pSalt;
  }

  @Override
  protected String getSaltForUser(String username) {
    return mSalt;
  }
}

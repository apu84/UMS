package org.ums.security.bearertoken;

import org.ums.usermanagement.user.User;

public interface ProfileRealm {

  public abstract boolean accountExists(String pUserId);

  public abstract User getAccount(String pUserId);

}

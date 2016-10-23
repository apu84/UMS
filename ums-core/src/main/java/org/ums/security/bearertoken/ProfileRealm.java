package org.ums.security.bearertoken;

import org.ums.domain.model.immutable.User;

public interface ProfileRealm {

  public abstract boolean accountExists(String pUserId) throws Exception;

  public abstract User getAccount(String pUserId) throws Exception;

}

package org.ums.manager;

import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.User;

public interface UserManager extends ContentManager<User, MutableUser, String> {
  void setPasswordResetToken(final String pToken, final String  pUserId)  throws Exception;
  void updatePassword(final String pUserId, final String  pPassword)  throws Exception;
  void clearPasswordResetToken(final String pUserId)  throws Exception;
}

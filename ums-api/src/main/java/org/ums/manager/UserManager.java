package org.ums.manager;

import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.immutable.User;

import java.util.List;

public interface UserManager extends ContentManager<User, MutableUser, String> {
  int setPasswordResetToken(final String pToken, final String pUserId);

  int updatePassword(final String pUserId, final String pPassword);

  int clearPasswordResetToken(final String pUserId);

  List<User> getUsers();
}

package org.ums.usermanagement.user;

import org.ums.manager.ContentManager;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

import java.util.List;

public interface UserManager extends ContentManager<User, MutableUser, String> {

  User getByEmployeeId(final String pEmployeeId);

  int setPasswordResetToken(final String pToken, final String pUserId);

  int updatePassword(final String pUserId, final String pPassword);

  int clearPasswordResetToken(final String pUserId);

  List<User> getUsers();
}

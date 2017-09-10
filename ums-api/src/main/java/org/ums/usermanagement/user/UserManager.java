package org.ums.usermanagement.user;

import org.ums.manager.ContentManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

import java.util.List;
import java.util.Optional;

public interface UserManager extends ContentManager<User, MutableUser, String>, UserEmail<User> {

  User getByEmployeeId(final String pEmployeeId);

  int setPasswordResetToken(final String pToken, final String pUserId);

  int updatePassword(final String pUserId, final String pPassword);

  int clearPasswordResetToken(final String pUserId);

  List<User> getUsers();

  List<User> getUsers(List<Role> pRoles);
}

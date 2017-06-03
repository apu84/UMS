package org.ums.usermanagement.user;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.List;

public class UserDaoDecorator extends ContentDaoDecorator<User, MutableUser, String, UserManager> implements
    UserManager {

  public User getByEmployeeId(final String pEmployeeId) {
    return getManager().getByEmployeeId(pEmployeeId);
  }

  @Override
  public int setPasswordResetToken(String pToken, String pUserId) {
    int modified = getManager().setPasswordResetToken(pToken, pUserId);
    if(modified <= 0) {
      throw new IllegalArgumentException("No entry updated");
    }
    return modified;
  }

  @Override
  public int updatePassword(String pUserId, String pPassword) {
    int modified = getManager().updatePassword(pUserId, pPassword);
    if(modified <= 0) {
      throw new IllegalArgumentException("No entry updated");
    }
    return modified;
  }

  @Override
  public int clearPasswordResetToken(final String pUserId) {
    int modified = getManager().clearPasswordResetToken(pUserId);
    if(modified <= 0) {
      throw new IllegalArgumentException("No entry updated");
    }
    return modified;
  }

  @Override
  public List<User> getUsers() {
    return getManager().getUsers();
  }

  @Override
  public List<User> getUsers(List<Role> pRoles) {
    return getManager().getUsers(pRoles);
  }
}

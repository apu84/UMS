package org.ums.usermanagement.user;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.enums.common.RoleType;
import org.ums.usermanagement.role.Role;

import java.util.List;
import java.util.Optional;

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

  @Override
  public Optional<User> getByEmail(String pEmail) {
    return getManager().getByEmail(pEmail);
  }

  @Override
  public List<User> getUsers(RoleType pRoleType) {
    return getManager().getUsers(pRoleType);
  }
}

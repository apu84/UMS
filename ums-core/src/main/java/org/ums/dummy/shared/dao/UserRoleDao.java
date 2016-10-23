package org.ums.dummy.shared.dao;

import org.ums.dummy.shared.model.User;
import org.ums.dummy.shared.model.UserRole;

import java.util.List;

public interface UserRoleDao {
  public List<UserRole> getRoles(String email);

  public void insertRole(UserRole role);
}

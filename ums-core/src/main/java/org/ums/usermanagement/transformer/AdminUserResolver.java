package org.ums.usermanagement.transformer;

import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

public class AdminUserResolver implements UserPropertyResolver {
  @Override
  public boolean supports(Role pRole) {
    return pRole.getName().equalsIgnoreCase("sadmin");
  }

  @Override
  public User resolve(User pUser) {
    MutableUser mutableUser = pUser.edit();
    mutableUser.setDepartment(null);
    mutableUser.setName("Admin User");
    mutableUser.setEmployeeId("-1");
    return mutableUser;
  }
}

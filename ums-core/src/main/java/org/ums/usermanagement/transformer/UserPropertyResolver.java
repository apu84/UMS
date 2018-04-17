package org.ums.usermanagement.transformer;

import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;

public interface UserPropertyResolver {
  boolean supports(Role pRole);

  User resolve(User pUser);
}

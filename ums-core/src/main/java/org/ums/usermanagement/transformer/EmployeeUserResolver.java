package org.ums.usermanagement.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.domain.model.immutable.Employee;
import org.ums.manager.EmployeeManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

public class EmployeeUserResolver implements UserPropertyResolver {
  private static final Logger mLogger = LoggerFactory.getLogger(EmployeeUserResolver.class);

  private EmployeeManager mEmployeeManager;

  public EmployeeUserResolver(EmployeeManager pEmployeeManager) {
    mEmployeeManager = pEmployeeManager;
  }

  @Override
  public boolean supports(Role pRole) {
    return true;
  }

  @Override
  public User resolve(User pUser) {
    MutableUser mutableUser = pUser.edit();
    Employee employee = mEmployeeManager.get(pUser.getEmployeeId());
    if(employee != null) {
      mutableUser.setDepartment(employee.getDepartment());
      mutableUser.setName(employee.getPersonalInformation().getName());
      mutableUser.setEmployeeId(employee.getId());
      mutableUser.setEmail(employee.getPersonalInformation().getPersonalEmail());
    }
    return mutableUser;
  }
}

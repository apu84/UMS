package org.ums.manager;

import org.ums.decorator.UserDaoDecorator;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableUser;

public class UserDepartmentResolver extends UserDaoDecorator {
  EmployeeManager mEmployeeManager;
  StudentManager mStudentManager;

  public UserDepartmentResolver(EmployeeManager pEmployeeManager, StudentManager pStudentManager) {
    mEmployeeManager = pEmployeeManager;
    mStudentManager = pStudentManager;
  }

  @Override
  public User get(String pId) throws Exception {
    User user = getManager().get(pId);
    MutableUser mutableUser = user.edit();
    if (user.getPrimaryRole().getName().equalsIgnoreCase("student")) {
      mutableUser.setDepartment(mStudentManager.get(pId).getDepartment());
    } else {
      Employee employee = null;
      try {
        employee = mEmployeeManager.getByEmployeeId(user.getEmployeeId());
      } catch (Exception e) {

      }
      if (employee != null) {
        mutableUser.setDepartment(employee.getDepartment());
      }
    }
    return user;
  }
}

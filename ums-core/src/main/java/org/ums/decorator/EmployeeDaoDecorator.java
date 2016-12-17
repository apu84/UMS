package org.ums.decorator;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.EmployeeManager;

import java.util.List;

public class EmployeeDaoDecorator extends
    ContentDaoDecorator<Employee, MutableEmployee, String, EmployeeManager> implements
    EmployeeManager {

  @Override
  public List<Employee> getByDesignation(String pDesignationId) {
    return getManager().getByDesignation(pDesignationId);
  }

  @Override
  public List<Employee> getActiveTeachersOfDept(String deptId) {
    return getManager().getActiveTeachersOfDept(deptId);
  }
}

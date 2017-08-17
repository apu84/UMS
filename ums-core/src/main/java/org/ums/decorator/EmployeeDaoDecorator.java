package org.ums.decorator;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.EmployeeManager;

import java.util.List;

public class EmployeeDaoDecorator extends ContentDaoDecorator<Employee, MutableEmployee, String, EmployeeManager>
    implements EmployeeManager {

  @Override
  public Employee getByEmail(final String pEmailAddress) {
    return getManager().getByEmail(pEmailAddress);
  }

  @Override
  public boolean emailExists(final String pEmailAddress) {
    return getManager().emailExists(pEmailAddress);
  }

  @Override
  public List<Employee> getByDesignation(String pDesignationId) {
    return getManager().getByDesignation(pDesignationId);
  }

  @Override
  public List<Employee> getActiveTeachersOfDept(String deptId) {
    return getManager().getActiveTeachersOfDept(deptId);
  }

  @Override
  public List<Employee> getEmployees(String pDeptId, String pPublicationStatus) {
    return getManager().getEmployees(pDeptId, pPublicationStatus);
  }

  @Override
  public List<Employee> getEmployees(String pDepartmentId) {
    return getManager().getEmployees(pDepartmentId);
  }

}

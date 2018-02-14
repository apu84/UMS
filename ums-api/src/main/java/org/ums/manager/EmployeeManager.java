package org.ums.manager;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.usermanagement.user.UserEmail;

import java.util.List;
import java.util.Optional;

public interface EmployeeManager extends ContentManager<Employee, MutableEmployee, String>, UserEmail<Employee> {
  List<Employee> getByDesignation(final String pDesignationId);

  List<Employee> getActiveTeachersOfDept(String deptId);

  List<Employee> getEmployees(String pDeptId, String pPublicationStatus);

  List<Employee> getEmployees(String pDepartmentId);

  String getLastEmployeeId(String pDepartmentId, int pEmployeeType);

  boolean validateShortName(String pShortName);
}

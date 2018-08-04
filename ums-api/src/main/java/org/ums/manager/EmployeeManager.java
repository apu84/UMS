package org.ums.manager;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;

import java.util.List;

public interface EmployeeManager extends ContentManager<Employee, MutableEmployee, String> {
  List<Employee> getByDesignation(final String pDesignationId);

  Employee getByShortName(final String pShortName);

  List<Employee> getActiveTeachersOfDept(String deptId);

  List<Employee> getEmployees(String pDeptId, String pPublicationStatus);

  List<Employee> getEmployees(String pDepartmentId);

  String getLastEmployeeId(String pDepartmentId, int pEmployeeType);

  boolean validateShortName(String pShortName);

  List<Employee> downloadEmployeeList(String pDeptList, String pEmployeeTypeList);
}

package org.ums.manager;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;

import java.util.List;

public interface EmployeeManager extends ContentManager<Employee, MutableEmployee, String> {

  boolean emailExists(final String pEmailAddress);

  Employee getByEmail(final String pEmailAddress);

  List<Employee> getByDesignation(final String pDesignationId);

  List<Employee> getActiveTeachersOfDept(String deptId);

  List<Employee> getEmployees(String pDeptId, String pPublicationStatus);

  List<Employee> getEmployees(String pDepartmentId);
}

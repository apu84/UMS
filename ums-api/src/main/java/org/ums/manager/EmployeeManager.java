package org.ums.manager;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;

import java.util.List;

public interface EmployeeManager extends ContentManager<Employee, MutableEmployee, String> {

  public boolean existenceByEmail(final String pEmailAddress);

  public Employee getByEmail(final String pEmailAddress);

  public List<Employee> getByDesignation(final String pDesignationId);

  public List<Employee> getActiveTeachersOfDept(String deptId);
}

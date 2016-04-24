package org.ums.decorator;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.EmployeeManager;

import java.util.List;


public class EmployeeDaoDecorator extends ContentDaoDecorator<Employee,MutableEmployee,String,EmployeeManager>  implements EmployeeManager {


  @Override
  public Employee getByEmployeeId(String pEmployeeId) {
    return getManager().getByEmployeeId(pEmployeeId);
  }

  @Override
  public List<Employee> getByDesignation(String pDesignationId) {
    return getManager().getByDesignation(pDesignationId);
  }
}

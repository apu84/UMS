package org.ums.decorator.registrar.Employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableEmployeeInformation;
import org.ums.manager.registrar.Employee.EmployeeInformationManager;

public class EmployeeInformationDaoDecorator extends
    ContentDaoDecorator<EmployeeInformation, MutableEmployeeInformation, Integer, EmployeeInformationManager> implements
    EmployeeInformationManager {
  @Override
  public int saveEmployeeInformation(MutableEmployeeInformation pMutableEmployeeInformation) {
    return getManager().saveEmployeeInformation(pMutableEmployeeInformation);
  }

  @Override
  public EmployeeInformation getEmployeeInformation(int pEmployeeId) {
    return getManager().getEmployeeInformation(pEmployeeId);
  }
}

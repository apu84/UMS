package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.MutableEmployeeInformation;
import org.ums.manager.registrar.EmployeeInformationManager;

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

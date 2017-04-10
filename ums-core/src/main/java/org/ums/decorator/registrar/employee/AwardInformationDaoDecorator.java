package org.ums.decorator.registrar.employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.employee.AwardInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableAwardInformation;
import org.ums.manager.registrar.employee.AwardInformationManager;

import java.util.List;

public class AwardInformationDaoDecorator extends
    ContentDaoDecorator<AwardInformation, MutableAwardInformation, Integer, AwardInformationManager> implements
    AwardInformationManager {

  @Override
  public int saveAwardInformation(MutableAwardInformation pMutableAwardInformation) {
    return getManager().saveAwardInformation(pMutableAwardInformation);
  }

  @Override
  public List<AwardInformation> getEmployeeAwardInformation(int pEmployeeId) {
    return getManager().getEmployeeAwardInformation(pEmployeeId);
  }
}

package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.AwardInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableAwardInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AwardInformationManager extends ContentManager<AwardInformation, MutableAwardInformation, Integer> {

  int saveAwardInformation(final MutableAwardInformation pMutableAwardInformation);

  List<AwardInformation> getEmployeeAwardInformation(final int pEmployeeId);
}

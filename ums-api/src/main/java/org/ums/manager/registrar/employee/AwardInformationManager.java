package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.AwardInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableAwardInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface AwardInformationManager extends ContentManager<AwardInformation, MutableAwardInformation, Integer> {

  int saveAwardInformation(final MutableAwardInformation pMutableAwardInformation);

  List<AwardInformation> getEmployeeAwardInformation(final int pEmployeeId);
}

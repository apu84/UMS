package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.MutableEmployeeInformation;
import org.ums.manager.ContentManager;

public interface EmployeeInformationManager extends
    ContentManager<EmployeeInformation, MutableEmployeeInformation, Integer> {

  int saveEmployeeInformation(final MutableEmployeeInformation pMutableEmployeeInformation);

  EmployeeInformation getEmployeeInformation(final int pEmployeeId);
}

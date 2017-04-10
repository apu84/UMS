package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableEmployeeInformation;
import org.ums.manager.ContentManager;

public interface EmployeeInformationManager extends
    ContentManager<EmployeeInformation, MutableEmployeeInformation, Integer> {

  int saveEmployeeInformation(final MutableEmployeeInformation pMutableEmployeeInformation);

  EmployeeInformation getEmployeeInformation(final int pEmployeeId);
}

package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.EmployeeInformation;
import org.ums.domain.model.immutable.registrar.Employee.PersonalInformation;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.domain.model.mutable.registrar.Employee.MutableEmployeeInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePersonalInformation;
import org.ums.manager.ContentManager;

public interface EmployeeInformationManager extends
    ContentManager<EmployeeInformation, MutableEmployeeInformation, Integer> {

  int saveEmployeeInformation(final MutableEmployeeInformation pMutableEmployeeInformation);

  EmployeeInformation getEmployeeInformation(final int pEmployeeId);
}

package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.PersonalInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePersonalInformation;
import org.ums.manager.ContentManager;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, Integer> {

  int savePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);

  PersonalInformation getEmployeePersonalInformation(final int pEmployeeId);
}

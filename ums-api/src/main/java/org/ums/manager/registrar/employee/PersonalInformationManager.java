package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.PersonalInformation;
import org.ums.domain.model.mutable.registrar.employee.MutablePersonalInformation;
import org.ums.manager.ContentManager;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, Integer> {

  int savePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);

  PersonalInformation getEmployeePersonalInformation(final int pEmployeeId);
}

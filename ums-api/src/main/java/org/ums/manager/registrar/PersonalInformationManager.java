package org.ums.manager.registrar;

import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.MutablePersonalInformation;
import org.ums.manager.ContentManager;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, String> {

  int savePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);

  PersonalInformation getPersonalInformation(final String pEmployeeId);

  int updatePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);

  int deletePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);
}

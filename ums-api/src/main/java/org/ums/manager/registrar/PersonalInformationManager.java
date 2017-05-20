package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.ContentManager;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, String> {

  int savePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);

  PersonalInformation getEmployeePersonalInformation(final String pEmployeeId);

  int deletePersonalInformation(final String pEmployeeId);

  int updatePersonalInformation(final MutablePersonalInformation pMutablePersonalInformation);
}

package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.registrar.PersonalInformationManager;

public class PersonalInformationDaoDecorator extends
    ContentDaoDecorator<PersonalInformation, MutablePersonalInformation, String, PersonalInformationManager> implements
    PersonalInformationManager {
  @Override
  public int savePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().savePersonalInformation(pMutablePersonalInformation);
  }

  @Override
  public PersonalInformation getPersonalInformation(String pEmployeeId) {
    return getManager().getPersonalInformation(pEmployeeId);
  }

  @Override
  public int updatePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().updatePersonalInformation(pMutablePersonalInformation);
  }

  @Override
  public int deletePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().deletePersonalInformation(pMutablePersonalInformation);
  }
}

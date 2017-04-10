package org.ums.decorator.registrar.employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.employee.PersonalInformation;
import org.ums.domain.model.mutable.registrar.employee.MutablePersonalInformation;
import org.ums.manager.registrar.employee.PersonalInformationManager;

public class PersonalInformationDaoDecorator extends
    ContentDaoDecorator<PersonalInformation, MutablePersonalInformation, Integer, PersonalInformationManager> implements
    PersonalInformationManager {

  @Override
  public int savePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().savePersonalInformation(pMutablePersonalInformation);
  }

  @Override
  public PersonalInformation getEmployeePersonalInformation(int pEmployeeId) {
    return getManager().getEmployeePersonalInformation(pEmployeeId);
  }
}

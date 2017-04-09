package org.ums.decorator.registrar.Employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.PersonalInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePersonalInformation;
import org.ums.manager.registrar.Employee.PersonalInformationManager;

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

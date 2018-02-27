package org.ums.employee.personal;

import org.ums.decorator.ContentDaoDecorator;

import java.util.Optional;

public class PersonalInformationDaoDecorator extends
    ContentDaoDecorator<PersonalInformation, MutablePersonalInformation, String, PersonalInformationManager> implements
    PersonalInformationManager {
  @Override
  public Optional<PersonalInformation> getByEmail(String pEmail) {
    return getManager().getByEmail(pEmail);
  }
  /*
   * @Override public int savePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { return
   * getManager().savePersonalInformation(pMutablePersonalInformation); }
   * 
   * @Override public PersonalInformation getPersonalInformation(String pEmployeeId) { return
   * getManager().getPersonalInformation(pEmployeeId); }
   * 
   * @Override public int updatePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { return
   * getManager().updatePersonalInformation(pMutablePersonalInformation); }
   * 
   * @Override public int deletePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { return
   * getManager().deletePersonalInformation(pMutablePersonalInformation); }
   */
}

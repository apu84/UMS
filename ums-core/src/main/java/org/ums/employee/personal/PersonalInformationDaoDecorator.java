package org.ums.employee.personal;

import org.ums.decorator.ContentDaoDecorator;

public class PersonalInformationDaoDecorator extends
    ContentDaoDecorator<PersonalInformation, MutablePersonalInformation, String, PersonalInformationManager> implements
    PersonalInformationManager {
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

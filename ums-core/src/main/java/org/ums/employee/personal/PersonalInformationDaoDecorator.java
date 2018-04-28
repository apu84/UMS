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
}

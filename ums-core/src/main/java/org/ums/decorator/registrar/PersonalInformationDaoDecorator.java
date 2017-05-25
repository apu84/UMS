package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.registrar.PersonalInformationManager;

public class PersonalInformationDaoDecorator extends
    ContentDaoDecorator<PersonalInformation, MutablePersonalInformation, String, PersonalInformationManager> implements
    PersonalInformationManager {
}

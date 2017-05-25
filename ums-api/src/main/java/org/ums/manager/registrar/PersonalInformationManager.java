package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.ContentManager;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, String> {
}

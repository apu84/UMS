package org.ums.employee.personal;

import org.ums.domain.model.immutable.Employee;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.MutablePersonalInformation;
import org.ums.manager.ContentManager;
import org.ums.usermanagement.user.UserEmail;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, String>, UserEmail<PersonalInformation> {
}

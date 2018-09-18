package org.ums.ems.profilemanagement.personal;

import org.ums.manager.ContentManager;
import org.ums.usermanagement.user.UserEmail;

public interface PersonalInformationManager extends
    ContentManager<PersonalInformation, MutablePersonalInformation, String>, UserEmail<PersonalInformation> {
}

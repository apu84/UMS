package org.ums.processor.userhome;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;
import org.ums.manager.UserManager;

public abstract class AbstractUserHomeProcessor implements UserHomeProcessor {
  protected static String ALLOWED_ROLES_SEPARATOR = ",";
  protected String mAllowedRole;

  @Autowired
  protected UserManager mUserManager;

  @Override
  public boolean supports(Subject pCurrentSubject) throws Exception {
    String userId = pCurrentSubject.getPrincipal().toString();
    User user = mUserManager.get(userId);

    if(user != null) {
      Role userRole = user.getPrimaryRole();
      if(mAllowedRole.contains(ALLOWED_ROLES_SEPARATOR)) {
        String[] allowedRoles = mAllowedRole.split(ALLOWED_ROLES_SEPARATOR);

        for(String allowedRole : allowedRoles) {
          if(allowedRole.equalsIgnoreCase(userRole.getName())) {
            return true;
          }
        }
      }
      else {
        return userRole.getName().equalsIgnoreCase(mAllowedRole);
      }
    }

    return false;
  }

  public String getAllowedRole() {
    return mAllowedRole;
  }

  public void setAllowedRole(String pAllowedRole) {
    mAllowedRole = pAllowedRole;
  }
}

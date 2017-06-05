package org.ums.processor.navigation;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.usermanagement.user.MutableUser;
import org.ums.domain.model.immutable.Navigation;
import org.ums.usermanagement.user.User;
import org.ums.manager.ContentManager;

public class DummyNavigationItemProcessor implements NavigationProcessor {
  @Autowired
  ContentManager<User, MutableUser, String> mUserManager;

  @Override
  public Navigation process(Navigation pNavigation, Subject pCurrentSubject) {
    User currentUser = mUserManager.get(pCurrentSubject.getPrincipal().toString());
    if(pNavigation.getLocation().contains("studentProfile")
        && currentUser.getPrimaryRole().getName().equalsIgnoreCase("sadmin")) {
      MutableNavigation mutableNavigation = pNavigation.edit();
      /**
       * Do some processing here
       */
      // mutableNavigation.setActive(false);
      return mutableNavigation;
    }

    return pNavigation;
  }
}

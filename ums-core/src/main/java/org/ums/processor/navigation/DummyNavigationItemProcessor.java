package org.ums.processor.navigation;


import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.immutable.Navigation;
import org.ums.domain.model.immutable.User;
import org.ums.manager.ContentManager;

public class DummyNavigationItemProcessor implements NavigationProcessor {
  @Autowired
  @Qualifier("userManager")
  ContentManager<User, MutableUser, String> mUserManager;

  @Override
  public Navigation process(Navigation pNavigation, Subject pCurrentSubject) throws Exception {
    User currentUser = mUserManager.get(pCurrentSubject.getPrincipal().toString());
    if (pNavigation.getLocation().contains("studentProfile")
        && currentUser.getPrimaryRole().getName().equalsIgnoreCase("sadmin")) {
      MutableNavigation mutableNavigation = pNavigation.edit();
      /**
       * Do some processing here
       */
//      mutableNavigation.setActive(false);
      return mutableNavigation;
    }

    return pNavigation;
  }
}

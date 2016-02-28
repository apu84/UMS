package org.ums.processor.navigation;

import org.apache.shiro.subject.Subject;
import org.ums.domain.model.readOnly.Navigation;

public interface NavigationProcessor {
  Navigation process(final Navigation pNavigation, final Subject pCurrentSubject) throws Exception;
}

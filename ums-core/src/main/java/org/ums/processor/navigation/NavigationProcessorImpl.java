package org.ums.processor.navigation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.ums.domain.model.immutable.Navigation;

import java.util.List;

public class NavigationProcessorImpl implements NavigationProcessor {
  private List<NavigationProcessor> mProcessorList;

  public NavigationProcessorImpl(final List<NavigationProcessor> pProcessorList) {
    mProcessorList = pProcessorList;
  }

  @Override
  public Navigation process(Navigation pNavigation, Subject pCurrentSubject) throws Exception {
    for(NavigationProcessor processor : mProcessorList) {
      pNavigation = processor.process(pNavigation, SecurityUtils.getSubject());
    }
    return pNavigation;
  }
}

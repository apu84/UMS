package org.ums.filter;

import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class NewUserFilter extends PathMatchingFilter {
  @Autowired
  UserManager mUserManager;
  private Collection<String> mAllowedResource;

  @Override
  protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    if(user.getTemporaryPassword() != null && user.getPassword() == null) {
      if(!isAllowed(request)) {
        return FilterUtil.sendUnauthorized(response);
      }
    }
    return super.onPreHandle(request, response, mappedValue);
  }

  public void setAllowedResource(Collection<String> pAllowedResource) {
    mAllowedResource = pAllowedResource;
  }

  private boolean isAllowed(ServletRequest pRequest) {
    for(String resource : mAllowedResource) {
      if(pathMatcher.matches(resource, getPathWithinApplication(pRequest))) {
        return true;
      }
    }
    return false;
  }
}

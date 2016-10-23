package org.ums.security.filter;

import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UMSHttpAuthenticationFilter extends BasicHttpAuthenticationFilter {

  protected List<String> mPassThrough;

  protected PatternMatcher mPathMatcher = new AntPathMatcher();

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
      Object mappedValue) {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    String httpMethod = httpRequest.getMethod();
    // TODO: Make this list of unauthenticated resource configurable
    return "OPTIONS".equalsIgnoreCase(httpMethod) || isPassThrough(request)
        || super.isAccessAllowed(request, response, mappedValue);
  }

  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = WebUtils.toHttp(response);
    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }

  protected boolean isPassThrough(ServletRequest pRequest) {
    for(String resource : mPassThrough) {
      if(mPathMatcher.matches(resource, getPathWithinApplication(pRequest))) {
        return true;
      }
    }
    return false;
  }

  public void setPassThrough(List<String> pPassThrough) {
    mPassThrough = pPassThrough;
  }
}

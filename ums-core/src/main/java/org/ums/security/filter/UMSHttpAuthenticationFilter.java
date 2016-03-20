package org.ums.security.filter;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UMSHttpAuthenticationFilter extends BasicHttpAuthenticationFilter {
  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse
      response, Object mappedValue) {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    String httpMethod = httpRequest.getMethod();
    String path = WebUtils.getPathWithinApplication(httpRequest);
    //TODO: Make this list of unauthenticated resource configurable
    return "OPTIONS".equalsIgnoreCase(httpMethod)
        || path.contains("forgotPassword")
        || super.isAccessAllowed(request, response, mappedValue);
  }

  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = WebUtils.toHttp(response);
    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }
}

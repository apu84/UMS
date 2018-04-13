package org.ums.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;

public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
  private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);
  @Autowired
  BearerAccessTokenManager mBearerAccessTokenManager;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    Subject subject = getSubject(request, response);
    String token = FilterUtil.getAuthToken((HttpServletRequest) request);
    try {
      log.info("[{}]: Logged out from the system", SecurityUtils.getSubject()
          .getPrincipal().toString());
      subject.logout();
    } catch(SessionException ise) {
      log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
    }
    invalidateToken(token);
    return false;
  }

  private void invalidateToken(String pToken) {
    List<BearerAccessToken> bearerAccessToken = mBearerAccessTokenManager.getByAccessToken(pToken);
    if(bearerAccessToken.size() > 0 && bearerAccessToken.get(0) != null) {
      bearerAccessToken.get(0).edit().delete();
    }
  }
}

package org.ums.filter;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;

public class SessionFilter extends PathMatchingFilter {
  private static final Logger mLogger = LoggerFactory.getLogger(SessionFilter.class);
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;
  private String mSigningKey;
  @Autowired
  private Integer sessionTimeout = 15;
  @Autowired
  private Integer sessionTimeoutInterval = 1;

  @Override
  protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    String jwt = FilterUtil.getAuthToken((HttpServletRequest) request);
    BearerAccessToken dbToken = mBearerAccessTokenManager.getByAccessToken(jwt).get(0);

    Date currentDate = new Date();
    long diff = currentDate.getTime() - dbToken.getLastAccessTime().getTime();
    long diffMinutes = diff / (60 * 1000);

    if(diffMinutes >= sessionTimeoutInterval && diffMinutes <= sessionTimeout) {
      MutableBearerAccessToken mutableBearerAccessToken = dbToken.edit();
      mutableBearerAccessToken.update();
      return true;
    }
    else if(diffMinutes > sessionTimeout) {
      if(mLogger.isDebugEnabled()) {
        mLogger.debug("Expired access token: " + dbToken.getId());
      }
      throw new AuthenticationException("Expired token");
    }
    return false;
  }

  public String getSigningKey() {
    return mSigningKey;
  }

  public void setSigningKey(String pSigningKey) {
    mSigningKey = pSigningKey;
  }
}

package org.ums.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.token.JwtsToken;

import io.jsonwebtoken.*;
import org.ums.token.TokenBuilder;

public class TokenFilter extends AuthenticatingFilter {
  private static final Logger mLogger = LoggerFactory.getLogger(TokenFilter.class);
  private TokenBuilder mTokenBuilder;

  @Override
  protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
    String jwt = FilterUtil.getAuthToken((HttpServletRequest) request);
    return new JwtsToken(mTokenBuilder.getUserName(jwt), jwt);
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest pRequest, ServletResponse pResponse, Object mappedValue) {
    boolean accessAllowed = false;
    String jwt = FilterUtil.getAuthToken((HttpServletRequest) pRequest);
    if(!mTokenBuilder.isValidToken(jwt)) {
      return false;
    }
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      String subjectName = (String) subject.getPrincipal();
      if(mTokenBuilder.getUserName(jwt).equals(subjectName)) {
        accessAllowed = true;
      }
    }
    return accessAllowed;
  }

  @Override
  protected boolean onAccessDenied(ServletRequest pRequest, ServletResponse pResponse) throws Exception {
    if(mTokenBuilder.isValidToken(FilterUtil.getAuthToken((HttpServletRequest) pRequest))) {
      return executeLogin(pRequest, pResponse);
    }
    return FilterUtil.sendUnauthorized(pResponse);
  }

  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
      ServletResponse response) {
    return FilterUtil.sendUnauthorized(response);
  }

  @Override
  protected boolean isRememberMe(ServletRequest request) {
    return false;
  }

  public void setTokenBuilder(TokenBuilder pTokenBuilder) {
    mTokenBuilder = pTokenBuilder;
  }
}

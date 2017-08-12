package org.ums.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class TokenFilter extends AuthenticatingFilter {
  private static final Logger mLogger = LoggerFactory.getLogger(TokenFilter.class);
  private String mSigningKey;

  @Override
  protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
    String jwt = FilterUtil.getAuthToken((HttpServletRequest) request);
    return new JwtsToken(getUserName(jwt), jwt);
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest pRequest, ServletResponse pResponse, Object mappedValue) {
    boolean accessAllowed = false;
    String jwt = FilterUtil.getAuthToken((HttpServletRequest) pRequest);
    if(!isValidToken(jwt)) {
      return false;
    }
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      String subjectName = (String) subject.getPrincipal();
      if(getUserName(jwt).equals(subjectName)) {
        accessAllowed = true;
      }
    }
    return accessAllowed;
  }

  @Override
  protected boolean onAccessDenied(ServletRequest pRequest, ServletResponse pResponse) throws Exception {
    if(isValidToken(FilterUtil.getAuthToken((HttpServletRequest) pRequest))) {
      return executeLogin(pRequest, pResponse);
    }
    return FilterUtil.sendUnauthorized(pResponse);
  }

  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
      ServletResponse response) {
    return FilterUtil.sendUnauthorized(response);
  }

  private String getUserName(final String jwt) throws ExpiredJwtException {
    Jws<Claims> claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(jwt);
    return claims.getBody().getSubject();
  }

  private boolean isValidToken(final String jwt) {
    return !StringUtils.isEmpty(jwt) && !isExpiredToken(jwt);
  }

  private boolean isExpiredToken(final String jwt) {
    boolean isExpired = false;
    try {
      getUserName(jwt);
    } catch(JwtException jwte) {
      isExpired = true;
    }
    return isExpired;
  }

  private String getSigningKey() {
    return mSigningKey;
  }

  public void setSigningKey(String pSigningKey) {
    mSigningKey = pSigningKey;
  }
}

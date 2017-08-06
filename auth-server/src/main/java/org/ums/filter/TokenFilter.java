package org.ums.filter;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.util.StringUtils;
import org.ums.token.JwtsToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class TokenFilter extends AuthenticatingFilter {
  private String mSigningKey;

  @Override
  protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
    String jwt = FilterUtil.getAuthToken((HttpServletRequest) request);
    if(StringUtils.isEmpty(jwt)) {
      return null;
    }
    return new JwtsToken(getUserName(jwt), jwt);
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest pRequest, ServletResponse pResponse, Object mappedValue) {
    boolean accessAllowed = false;
    HttpServletRequest httpRequest = (HttpServletRequest) pRequest;
    String jwt = FilterUtil.getAuthToken(httpRequest);
    if(StringUtils.isEmpty(jwt)) {
      return false;
    }
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      String subjectName = (String) subject.getPrincipal();
      if(getUserName(jwt).equals(subjectName) && getTokenExpiry(jwt).before(new Date())) {
        accessAllowed = true;
      }
    }
    return accessAllowed;
  }

  @Override
  protected boolean onAccessDenied(ServletRequest pRequest, ServletResponse pResponse) throws Exception {
    if(!StringUtils.isEmpty(FilterUtil.getAuthToken((HttpServletRequest) pRequest))) {
      return executeLogin(pRequest, pResponse);
    }
    return sendUnauthorized((HttpServletResponse) pResponse);
  }

  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
      ServletResponse response) {
    return sendUnauthorized((HttpServletResponse) response);
  }

  private boolean sendUnauthorized(HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }

  private String getUserName(final String jwt) {
    Jws<Claims> claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(jwt);
    return claims.getBody().getSubject();
  }

  private Date getTokenExpiry(final String jwt) {
    Jws<Claims> claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(jwt);
    return claims.getBody().getExpiration();
  }

  private String getSigningKey() {
    return mSigningKey;
  }

  public void setSigningKey(String pSigningKey) {
    mSigningKey = pSigningKey;
  }
}

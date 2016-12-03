package org.ums.security.bearertoken.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.util.WebUtils;
import org.ums.security.bearertoken.BearerToken;
import org.ums.security.bearertoken.util.HTTP;
import org.ums.security.bearertoken.util.Messages;
import org.ums.security.bearertoken.util.MimeTypes;
import org.ums.security.filter.UMSHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Locale;

public final class BearerTokenAuthenticatingFilter extends UMSHttpAuthenticationFilter {

  private static final String AUTHORIZATION_HEADER = "X-Authorization";
  private static final String AUTHORIZATION_PARAM = "ums_auth";
  private static final String AUTHORIZATION_SCHEME = "UMSTOKEN";
  private static final String AUTHORIZATION_SCHEME_ALT = "Basic";
  private static final String LOGIN_AS_SEPARATOR = ">";
  private String usernameParam;
  private String passwordParam;

  public void setUsernameParam(String usernameParam) {
    this.usernameParam = usernameParam;
  }

  public void setPasswordParam(String passwordParam) {
    this.passwordParam = passwordParam;
  }

  String getUsernameParam() {
    return usernameParam;
  }

  String getPasswordParam() {
    return passwordParam;
  }

  @Override
  protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
    if(isLoginRequest(request, response)) {
      return super.createToken(request, response);
    }
    else {
      String authorizeHeader = getAuthorizationHeader(request);
      String authorizeParameter = getAuthorizationParameter(request);
      String[] principlesAndCredentials;

      if(isHeaderLoginAttempt(authorizeHeader)) {
        principlesAndCredentials = this.getHeaderPrincipalsAndCredentials(authorizeHeader);
      }
      else if(isParameterLoginAttempt(authorizeParameter)) {
        principlesAndCredentials = this.getParameterPrincipalsAndCredentials(authorizeParameter);
      }
      else {
        return null;
      }

      if(principlesAndCredentials == null || principlesAndCredentials.length != 2) {
        return null;
      }

      String username = principlesAndCredentials[0];
      String token = principlesAndCredentials[1];

      return new BearerToken(username, token, WebUtils.toHttp(request).getPathInfo());
    }
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
      throws Exception {
    boolean authHasToken = hasAuthorizationToken(request);
    boolean isLogin = isLoginRequest(request, response);
    if(authHasToken || isLogin) {
      return executeLogin(request, response);
    }
    else {
      HTTP.writeError(response, HTTP.Status.UNAUTHORIZED);
      return false;
    }
  }

  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
      ServletRequest request, ServletResponse response) {
    boolean isLogin = isLoginRequest(request, response);
    try {
      if(isLogin) {
        HTTP.writeError(response, HTTP.Status.UNAUTHORIZED);
      }
      else {
        HTTP.write(response, MimeTypes.PLAINTEXT, HTTP.Status.UNAUTHORIZED,
            Messages.Status.EXPIRED_TOKEN.toString());
      }
    } catch(IOException ie) {
      throw new UncheckedIOException(ie);
    }
    return false;
  }

  @Override
  public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
      throws Exception {
    return isLoginRequest(request, response) && hasAuthorizationToken(request)
        || super.onPreHandle(request, response, mappedValue);
  }

  boolean hasAuthorizationToken(ServletRequest request) {
    String authorizeHeader = getAuthorizationHeader(request);
    String authorizeParam = getAuthorizationParameter(request);
    return isHeaderLoginAttempt(authorizeHeader) || isParameterLoginAttempt(authorizeParam);
  }

  String getAuthorizationHeader(ServletRequest request) {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    return httpRequest.getHeader(AUTHORIZATION_HEADER);
  }

  String getAuthorizationParameter(ServletRequest request) {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    return WebUtils.getCleanParam(httpRequest, AUTHORIZATION_PARAM);
  }

  boolean isHeaderLoginAttempt(String authorizeHeader) {
    if(authorizeHeader == null)
      return false;
    String authorizeScheme = AUTHORIZATION_SCHEME.toLowerCase(Locale.ENGLISH);
    String authorizeSchemeAlt = AUTHORIZATION_SCHEME_ALT.toLowerCase(Locale.ENGLISH);
    String test = authorizeHeader.toLowerCase(Locale.ENGLISH);
    return test.startsWith(authorizeScheme) || test.startsWith(authorizeSchemeAlt);
  }

  boolean isParameterLoginAttempt(String authorizeParam) {
    return (authorizeParam != null) && Base64.isBase64(authorizeParam.getBytes());
  }

  String[] getHeaderPrincipalsAndCredentials(String authorizeHeader) {
    if(authorizeHeader == null) {
      return null;
    }
    String[] authTokens = authorizeHeader.split(" ");
    if(authTokens == null || authTokens.length < 2) {
      return null;
    }
    return getPrincipalsAndCredentials(authTokens[1]);
  }

  String[] getParameterPrincipalsAndCredentials(String authorizeParam) {
    if(authorizeParam == null) {
      return null;
    }
    return getPrincipalsAndCredentials(authorizeParam);
  }

  String[] getPrincipalsAndCredentials(String encoded) {
    String decoded = Base64.decodeToString(encoded);
    String[] decodedArray = decoded.split(":", 2);
    if(decodedArray[0].contains(LOGIN_AS_SEPARATOR)) {
      decodedArray[0] = decodedArray[0].split(LOGIN_AS_SEPARATOR)[1];
    }
    return decodedArray;
  }

  String getUsername(ServletRequest request) {
    return WebUtils.getCleanParam(request, getUsernameParam());
  }

  String getPassword(ServletRequest request) {
    return WebUtils.getCleanParam(request, getPasswordParam());
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
      Object mappedValue) {
    return !(!isLoginRequest(request, response) && isPermissive(mappedValue) && hasAuthorizationToken(request))
        && (super.isAccessAllowed(request, response, mappedValue) || (!isLoginRequest(request,
            response) && isPermissive(mappedValue) && !hasAuthorizationToken(request)));
  }

}

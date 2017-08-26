package org.ums.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.persistent.model.PersistentBearerAccessToken;
import org.ums.token.Token;
import org.ums.token.TokenBuilder;

public class LoginFilter extends BasicHttpAuthenticationFilter {
  @Autowired
  private TokenBuilder mTokenBuilder;

  @Override
  protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
      ServletResponse response) throws Exception {
    Token accessToken = mTokenBuilder.accessToken();
    Token refreshToken = mTokenBuilder.refreshToken();
    persistToken(accessToken, refreshToken);
    response.setContentType("application/json");
    ((HttpServletResponse) response).addCookie(FilterUtil.refreshTokenCookie(refreshToken, request.getServletContext()
        .getContextPath()));
    PrintWriter out = response.getWriter();
    out.print(FilterUtil.accessTokenJson(accessToken));
    out.flush();
    return false;
  }

  @Override
  protected boolean isRememberMe(ServletRequest request) {
    return false;
  }

  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = WebUtils.toHttp(response);
    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }

  private void persistToken(Token accessToken, Token refreshToken) {
    MutableBearerAccessToken token = new PersistentBearerAccessToken();
    token.setUserId(SecurityUtils.getSubject().getPrincipal().toString());
    token.setId(accessToken.getHash());
    token.setRefreshToken(refreshToken.getHash());
    token.create();
  }
}

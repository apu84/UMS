package org.ums.filter;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.persistent.model.PersistentBearerAccessToken;
import org.ums.token.Token;
import org.ums.token.TokenBuilder;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class RefreshTokenFilter extends AbstractPathMatchingFilter {
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;
  @Autowired
  private TokenBuilder mTokenBuilder;

  private String mSigningKey;

  @Override
  @Transactional
  public boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    Optional<String> refreshToken = extractRefreshToken((HttpServletRequest) request);
    if(refreshToken.isPresent()) {
      List<BearerAccessToken> tokens = mBearerAccessTokenManager.getByRefreshToken(refreshToken.get());
      if(tokens.size() > 0 && isValidToken(refreshToken.get())) {
        MutableBearerAccessToken mutable = new PersistentBearerAccessToken();
        Token accessToken = mTokenBuilder.accessToken();
        mutable.setId(accessToken.getHash());
        mutable.setUserId(tokens.get(0).getUserId());
        mutable.setRefreshToken(tokens.get(0).getRefreshToken());
        mutable.create();

        PrintWriter out = response.getWriter();
        out.print(FilterUtil.accessTokenJson(accessToken));
        out.flush();
      }
      else {
        sendError("Invalid refresh token", response);
      }
    }
    return false;
  }

  private Optional<String> extractRefreshToken(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if(cookies != null) {
      for(Cookie cookie : cookies) {
        if(cookie.getName().equalsIgnoreCase("refreshToken")) {
          return Optional.of(cookie.getValue());
        }
      }
    }
    return Optional.empty();
  }

  private boolean isValidToken(final String token) {
    boolean valid = true;
    try {
      Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
    } catch(JwtException jwte) {
      valid = false;
    }
    return valid;
  }

  private String getSigningKey() {
    return mSigningKey;
  }

  public void setSigningKey(String pSigningKey) {
    mSigningKey = pSigningKey;
  }
}

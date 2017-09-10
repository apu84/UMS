package org.ums.filter;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ums.token.Token;

class FilterUtil {
  static String getAuthToken(HttpServletRequest pHttpRequest) {
    String jwt = pHttpRequest.getHeader("Authorization");
    if(jwt == null || !jwt.startsWith("Bearer ")) {
      return null;
    }
    return jwt.substring(jwt.indexOf(" ")).trim();
  }

  static boolean sendUnauthorized(ServletResponse response) {
    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }

  static String accessTokenJson(Token pAccessToken) {
    return String.format("{\"access_token\": \"Bearer %s\", \"expires_in\": %d}", pAccessToken.getHash(),
        pAccessToken.getTokenExpiry());
  }

  static Cookie refreshTokenCookie(Token refreshToken, String path) {
    Cookie cookie = new Cookie("refreshToken", refreshToken.getHash());
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath(path + "/refreshToken");
    cookie.setMaxAge((int) refreshToken.getTokenExpiry());
    return cookie;
  }
}

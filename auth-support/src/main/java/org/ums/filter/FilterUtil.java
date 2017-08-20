package org.ums.filter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}

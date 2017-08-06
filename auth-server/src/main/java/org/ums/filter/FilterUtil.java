package org.ums.filter;

import javax.servlet.http.HttpServletRequest;

class FilterUtil {
  static String getAuthToken(HttpServletRequest pHttpRequest) {
    String jwt = pHttpRequest.getHeader("Authorization");
    if(jwt == null || !jwt.startsWith("Bearer ")) {
      return null;
    }
    return jwt.substring(jwt.indexOf(" ")).trim();
  }
}

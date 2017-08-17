package org.ums.filter;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractPathMatchingFilter extends PathMatchingFilter {
  JSONObject getRequestJson(HttpServletRequest pHttpServletRequest) throws IOException, ParseException {
    String content = IOUtils.toString(pHttpServletRequest.getInputStream());
    JSONParser parser = new JSONParser();
    return (JSONObject) parser.parse(content);
  }

  boolean sendError(String error, ServletResponse pResponse) throws IOException {
    ((HttpServletResponse) pResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, error);
    return false;
  }
}

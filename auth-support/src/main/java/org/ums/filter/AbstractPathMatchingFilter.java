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
import java.io.PrintWriter;

public abstract class AbstractPathMatchingFilter extends PathMatchingFilter {
  JSONObject getRequestJson(HttpServletRequest pHttpServletRequest) throws IOException, ParseException {
    String content = IOUtils.toString(pHttpServletRequest.getInputStream());
    JSONParser parser = new JSONParser();
    return (JSONObject) parser.parse(content);
  }

  boolean sendError(String error, ServletResponse pResponse) throws IOException {
    ((HttpServletResponse) pResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    pResponse.setContentType("application/json");
    PrintWriter out = pResponse.getWriter();
    out.print(error);
    out.flush();
    return false;
  }

  boolean sendSuccess(String message, ServletResponse pResponse) throws IOException {
    ((HttpServletResponse) pResponse).setStatus(HttpServletResponse.SC_OK);
    pResponse.setContentType("application/json");
    PrintWriter out = pResponse.getWriter();
    out.print(message);
    out.flush();
    return false;
  }
}

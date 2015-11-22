package org.ums.dummy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import org.ums.dummy.DummyService;

/**
 * Servlet implementation class for Servlet: FirstServlet
 */
public class DummyServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // For this sample, we simply perform depency lookup from the current WebApplicationContext.
    // This will query the shared parent context and find the sampleService bean.
    // In a real web app, Spring provide a better way to do dependency injection
    // of your web controllers/actions/whatever your web framework calls them.
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    DummyService service = (DummyService) ctx.getBean("dummyService");

    response.getWriter().println(service.getMessage() + " using service instance " + service);
  }

}
package org.ums.dummy.webservice;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import org.ums.dummy.shared.DummyService;

@Component
public class DummyServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

  @Autowired
  private DummyService dummyService;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // For this sample, we simply perform dependency lookup from the current WebApplicationContext.
    // This will query the shared parent context and find the sampleService bean.
    // In a real web app, Spring provide a better way to do dependency injection
    // of your web controllers/actions/whatever your web framework calls them.
    /*
     * ApplicationContext ctx =
     * WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()); DummyService
     * service = (DummyService) ctx.getBean("dummyService");
     */

    response.getWriter().println(dummyService.getMessage());
    response.getWriter().println("Using service instance ums-webservice-academic");
    response.getWriter().println(dummyService);
  }

  public void init(ServletConfig config) {
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }

}

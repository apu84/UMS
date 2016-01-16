package org.ums.common.report.generator;


import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.regular.Student;
import org.ums.domain.model.regular.User;
import org.ums.manager.ContentManager;
import org.ums.report.generator.AbstractReportGenerator;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class PasswordReportGenerator extends AbstractReportGenerator {
  @Autowired
  @Qualifier("userManager")
  ContentManager<User, MutableUser, String> mUserManager;

  @Autowired
  @Qualifier("studentManager")
  ContentManager<Student, MutableStudent, String> mStudentManager;

  @Override
  public MasterReport getReportDefinition() throws Exception {
    // Using the classloader, get the URL to the reportDefinition file
    final ClassLoader classloader = this.getClass().getClassLoader();
    final URL reportDefinitionURL = classloader.getResource("report/password/generatedPassword.prpt");

    // Parse the report file
    final ResourceManager resourceManager = new ResourceManager();
    final Resource directly = resourceManager.createDirectly(reportDefinitionURL, MasterReport.class);
    return (MasterReport) directly.getResource();
  }

  @Override
  public DataFactory getDataFactory() {
    return null;
  }

  @Override
  public Map<String, Object> getReportParameters(Object... pParameter) throws Exception {
    final Map<String, Object> parameters = new HashMap<>();
    if (pParameter.length > 0) {
      User user = mUserManager.get(pParameter[0].toString());
      parameters.put("user id", user.getId());
      parameters.put("credential", String.valueOf(user.getTemporaryPassword()));

      Student student = mStudentManager.get(pParameter[0].toString());
      parameters.put("user name", student.getFullName());
      parameters.put("role name", user.getRole().getName());
      parameters.put("created on", new Date());
      parameters.put("department", student.getDepartment().getShortName());
      parameters.put("semester", "1st year, 1st semester");
      parameters.put("role", user.getRole().getName());
    }
    return parameters;
  }

}

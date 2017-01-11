package org.ums.report.generator;

import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.StudentManager;
import org.ums.manager.UserManager;
import org.ums.report.generator.AbstractReportGenerator;

import java.net.URL;
import java.util.Map;

@Component
public class PasswordReportGenerator extends AbstractReportGenerator {
  @Autowired
  UserManager mUserManager;

  @Autowired
  StudentManager mStudentManager;

  private static final String QUERY_NAME = "ReportQuery";

  @Override
  public MasterReport getReportDefinition() throws Exception {
    // Using the classloader, get the URL to the reportDefinition file
    final ClassLoader classloader = this.getClass().getClassLoader();
    final URL reportDefinitionURL =
        classloader.getResource("report/password/generatedPassword.prpt");

    // Parse the report file
    final ResourceManager resourceManager = new ResourceManager();
    final Resource directly =
        resourceManager.createDirectly(reportDefinitionURL, MasterReport.class);
    return (MasterReport) directly.getResource();
  }

  public DataFactory getDataFactory(String reportQuery) {
    // TODO : Refactor this connection to use the common connection used for the application
    final DriverConnectionProvider sampleDriverConnectionProvider = new DriverConnectionProvider();
    sampleDriverConnectionProvider.setDriver("oracle.jdbc.OracleDriver");
    sampleDriverConnectionProvider.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
    sampleDriverConnectionProvider.setProperty("user", "DB_IUMS");
    sampleDriverConnectionProvider.setProperty("password", "ig100");

    final SQLReportDataFactory dataFactory =
        new SQLReportDataFactory(sampleDriverConnectionProvider);
    dataFactory.setQuery(QUERY_NAME, reportQuery);

    return dataFactory;
  }

  @Override
  public Map<String, Object> getReportParameters(Object... pParameter) {
    return null;
  }

  /*
   * public Map<String, Object> getReportParameters(Object... pParameter) { final Map<String,
   * Object> parameters = new HashMap<>(); if (pParameter.length > 0) { User user =
   * mUserManager.get(pParameter[0].toString()); parameters.put("user id", user.getId());
   * parameters.put("credential", String.valueOf(user.getTemporaryPassword()));
   * 
   * Student student = mStudentManager.get(pParameter[0].toString()); parameters.put("user name",
   * student.getFullName()); parameters.put("role name", user.getRole().getName());
   * parameters.put("created on", new Date()); parameters.put("department",
   * student.getDepartment().getShortName()); parameters.put("semester", "1st year, 1st semester");
   * parameters.put("role", user.getRole().getName()); } return parameters; }
   */

}

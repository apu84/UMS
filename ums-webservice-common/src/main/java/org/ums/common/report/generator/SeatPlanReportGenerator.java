package org.ums.common.report.generator;

import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.SeatPlanManager;
import org.ums.report.generator.AbstractReportGenerator;

import java.util.Map;

/**
 * Created by My Pc on 5/9/2016.
 */
@Component
public class SeatPlanReportGenerator extends AbstractReportGenerator {
  @Autowired
  SeatPlanManager mSeatPlanManager;

  private static final String QUERY_NAME = "ReportQuery";

  @Override
  public MasterReport getReportDefinition() throws Exception {
    return null;
  }




  @Override
  public DataFactory getDataFactory(String reportQuery) throws Exception {
    final DriverConnectionProvider sampleDriverConnectionProvider = new DriverConnectionProvider();
    sampleDriverConnectionProvider.setDriver("oracle.jdbc.OracleDriver");
    sampleDriverConnectionProvider.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
    sampleDriverConnectionProvider.setProperty("user", "DB_IUMS");
    sampleDriverConnectionProvider.setProperty("password", "ig100");

    final SQLReportDataFactory dataFactory = new SQLReportDataFactory(sampleDriverConnectionProvider);
    dataFactory.setQuery(QUERY_NAME,reportQuery);

    return dataFactory;
  }

  @Override
  public Map<String, Object> getReportParameters(Object... pParameters) throws Exception {
    return null;
  }
}

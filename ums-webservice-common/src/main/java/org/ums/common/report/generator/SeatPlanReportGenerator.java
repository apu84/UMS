package org.ums.common.report.generator;

import com.itextpdf.text.DocumentException;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.SeatPlanManager;
import org.ums.report.generator.AbstractReportGenerator;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.util.Map;

/**
 * Created by My Pc on 5/9/2016.
 */

public interface SeatPlanReportGenerator {
  StreamingOutput createPdf(String dest,boolean noSeatPlanInfo,int pSemesterId,int groupNo,int type) throws Exception,IOException,DocumentException;
}

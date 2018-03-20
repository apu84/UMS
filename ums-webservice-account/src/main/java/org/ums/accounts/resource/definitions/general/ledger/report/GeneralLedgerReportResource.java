package org.ums.accounts.resource.definitions.general.ledger.report;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.report.general.ledger.GeneralLedgerReportGenerator;
import org.ums.util.UmsUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 20-Mar-18.
 */
@Component
@Path("/account/general-ledger-report")
public class GeneralLedgerReportResource {
  private static final Logger mLogger = org.slf4j.LoggerFactory.getLogger(GeneralLedgerReportGenerator.class);

  @Autowired
  private GeneralLedgerReportGenerator mGeneralLedgerReportGenerator;

  @GET
  @Produces("application/pdf")
  public StreamingOutput createReport(final @Context HttpServletResponse pHttpServletResponse,
      @QueryParam("accountId") String pAccountId, @QueryParam("groupCode") String pGroupCode,
      @QueryParam("fromDate") String pFromDate, @QueryParam("toDate") String pToDate) throws Exception {
    mLogger.info("Should print general ledger report");
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mGeneralLedgerReportGenerator.createReport(Long.parseLong(pAccountId), pGroupCode,
              UmsUtils.convertToDate(pFromDate, "dd-MM-yyyy"), UmsUtils.convertToDate(pToDate, "dd-MM-yyyy"), output);
        } catch(Exception e) {
          mLogger.info(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

}

package org.ums.accounts.resource.report.balance.sheet;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.enums.accounts.general.ledger.reports.BalanceSheetFetchType;
import org.ums.report.balance.sheet.BalanceSheetReportGenerator;
import org.ums.util.UmsUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 31-Mar-18.
 */
@Component
@Path("/account/report/balance-sheet")
public class BalanceSheetReportResource {

  private static final Logger mLogger = org.slf4j.LoggerFactory.getLogger(BalanceSheetReportGenerator.class);

  @Autowired
  private BalanceSheetReportGenerator mBalanceSheetReportGenerator;

  @GET
  @Produces("application/pdf")
  @Path("/asOnDate/{as-on-date}/fetchType/{fetch-type}/debtorLedgerFetchType/{debtor-ledger-fetch-type}")
  public StreamingOutput createReport(final @Context HttpServletResponse pHttpServletResponse,
      @PathParam("as-on-date") String pAsOnDate, @PathParam("fetch-type") String pFetchType,
      @PathParam("debtor-ledger-fetch-type") String pDebtorLedgerFetchType) throws Exception {
    mLogger.info("Should print balance sheet report");
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mBalanceSheetReportGenerator.createBalanceSheetReport(UmsUtils.convertToDate(pAsOnDate, "dd-MM-yyyy"),
              BalanceSheetFetchType.get(pFetchType), BalanceSheetFetchType.get(pDebtorLedgerFetchType), output);
        } catch(Exception e) {
          mLogger.info(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }
}

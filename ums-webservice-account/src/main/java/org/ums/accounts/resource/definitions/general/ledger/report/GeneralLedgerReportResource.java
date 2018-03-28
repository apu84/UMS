package org.ums.accounts.resource.definitions.general.ledger.report;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.enums.accounts.general.ledger.reports.FetchType;
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
@Path("/account/report/general-ledger-report")
public class GeneralLedgerReportResource {
  private static final Logger mLogger = org.slf4j.LoggerFactory.getLogger(GeneralLedgerReportGenerator.class);

  @Autowired
  private GeneralLedgerReportGenerator mGeneralLedgerReportGenerator;

  @GET
  @Produces("application/pdf")
  @Path("/accountId/{accountId}/groupCode/{groupCode}/fromDate/{fromDate}/toDate/{toDate}/fetchType/{fetchType}")
  public StreamingOutput createReport(final @Context HttpServletResponse pHttpServletResponse,
      @PathParam("accountId") String pAccountId, @PathParam("groupCode") String pGroupCode,
      @PathParam("fromDate") String pFromDate, @PathParam("toDate") String pToDate,
      @PathParam("fetchType") String pFetchType) throws Exception {
    mLogger.info("Should print general ledger report");
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mGeneralLedgerReportGenerator.createReport(
              pAccountId.equals("null") || pAccountId == null ? null : Long.parseLong(pAccountId),
              pGroupCode.equals("null") || pGroupCode == null ? null : pGroupCode,
              UmsUtils.convertToDate(pFromDate, "dd-MM-yyyy"), UmsUtils.convertToDate(pToDate, "dd-MM-yyyy"),
              FetchType.get(pFetchType), output);
        } catch(Exception e) {
          mLogger.info(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

}

package org.ums.report.general.ledger;

import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Mar-18.
 */
public interface GeneralLedgerReportGenerator {
  void createReport(Long pAccountId, String pGroupId, Date fromDate, Date toDate, OutputStream pOutputStream);

}

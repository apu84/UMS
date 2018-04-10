package org.ums.report.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;

import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 10-Apr-18.
 */
@Service
public class TransactionReportGenerator {

  @Autowired
  private AccountTransactionManager mTransactionManager;
  @Autowired
  private CompanyManager mCompanyManager;

  public void createVoucherReport(String pVoucherNo, Date pVoucherDate, OutputStream pOutputStream) throws Exception {

  }
}

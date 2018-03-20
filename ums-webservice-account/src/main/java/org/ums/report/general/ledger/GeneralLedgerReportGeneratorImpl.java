package org.ums.report.general.ledger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.AccountTransactionManager;

import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Mar-18.
 */
@Service
public class GeneralLedgerReportGeneratorImpl implements GeneralLedgerReportGenerator {
  @Autowired
  private AccountTransactionManager mAccountTransactionManager;
  @Autowired
  private AccountManager mAccountManager;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;

  @Override
  public void createReport(Long pAccountId, String pGroupId, Date fromDate, Date toDate, OutputStream pOutputStream) {

  }
}

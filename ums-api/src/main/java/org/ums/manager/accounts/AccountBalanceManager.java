package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.manager.ContentManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public interface AccountBalanceManager extends ContentManager<AccountBalance, MutableAccountBalance, Long> {

  AccountBalance getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate, Account pAccount);

  List<AccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate);

  List<MutableAccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate,
      List<Account> pAccounts);

  Long insertFromAccount(MutableAccountBalance pAccountBalance);

}

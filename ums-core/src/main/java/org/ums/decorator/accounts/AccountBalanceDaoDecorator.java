package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.manager.accounts.AccountBalanceManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public class AccountBalanceDaoDecorator extends
    ContentDaoDecorator<AccountBalance, MutableAccountBalance, Long, AccountBalanceManager> implements
    AccountBalanceManager {

  @Override
  public Long insertFromAccount(MutableAccountBalance pAccountBalance) {
    return getManager().insertFromAccount(pAccountBalance);
  }

  @Override
  public AccountBalance getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate, Account pAccount) {
    return getManager().getAccountBalance(pFinancialStartDate, pFinancialEndDate, pAccount);
  }

  @Override
  public List<AccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate) {
    return getManager().getAccountBalance(pFinancialStartDate, pFinancialEndDate);
  }

  @Override
  public List<MutableAccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate,
      List<Account> pAccounts) {
    return getManager().getAccountBalance(pFinancialStartDate, pFinancialEndDate, pAccounts);
  }
}

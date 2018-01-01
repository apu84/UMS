package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.manager.accounts.AccountBalanceManager;

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
}

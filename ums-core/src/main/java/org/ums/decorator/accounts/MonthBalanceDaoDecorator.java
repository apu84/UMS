package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.MonthBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;
import org.ums.manager.accounts.MonthBalanceManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 09-Feb-18.
 */
public class MonthBalanceDaoDecorator extends
    ContentDaoDecorator<MonthBalance, MutableMonthBalance, Long, MonthBalanceManager> implements MonthBalanceManager {
  @Override
  public List<MutableMonthBalance> getExistingMonthBalanceBasedOnAccountBalance(
      List<MutableAccountBalance> pAccountBalances, Long pMonthId) {
    return getManager().getExistingMonthBalanceBasedOnAccountBalance(pAccountBalances, pMonthId);
  }
}

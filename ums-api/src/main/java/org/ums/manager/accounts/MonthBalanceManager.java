package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.MonthBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 09-Feb-18.
 */
public interface MonthBalanceManager extends ContentManager<MonthBalance, MutableMonthBalance, Long> {
  List<MutableMonthBalance> getExistingMonthBalanceBasedOnAccountBalance(List<MutableAccountBalance> pAccountBalances,
      Long pMonthId);
}

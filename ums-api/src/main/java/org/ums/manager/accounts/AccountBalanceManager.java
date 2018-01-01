package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public interface AccountBalanceManager extends ContentManager<AccountBalance, MutableAccountBalance, Long> {
  Long insertFromAccount(MutableAccountBalance pAccountBalance);
}

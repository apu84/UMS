package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.DebtorLedger;
import org.ums.domain.model.mutable.accounts.MutableDebtorLedger;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public interface DebtorLedgerManager extends ContentManager<DebtorLedger, MutableDebtorLedger, Long> {
  List<MutableDebtorLedger> get(List<AccountTransaction> pAccountTransactions);
}

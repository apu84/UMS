package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.CreditorLedger;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public interface CreditorLedgerManager extends ContentManager<CreditorLedger, MutableCreditorLedger, Long> {
  List<MutableCreditorLedger> get(List<AccountTransaction> pAccountTransactions);
}

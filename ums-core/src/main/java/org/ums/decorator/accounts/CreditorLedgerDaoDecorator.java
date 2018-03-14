package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.CreditorLedger;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;
import org.ums.manager.accounts.CreditorLedgerManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class CreditorLedgerDaoDecorator extends
    ContentDaoDecorator<CreditorLedger, MutableCreditorLedger, Long, CreditorLedgerManager> implements
    CreditorLedgerManager {

  @Override
  public List<MutableCreditorLedger> get(List<AccountTransaction> pAccountTransactions) {
    return getManager().get(pAccountTransactions);
  }
}

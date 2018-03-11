package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.DebtorLedger;
import org.ums.domain.model.mutable.accounts.MutableDebtorLedger;
import org.ums.manager.accounts.DebtorLedgerManager;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class DebtorLedgerDaoDecorator extends
    ContentDaoDecorator<DebtorLedger, MutableDebtorLedger, Long, DebtorLedgerManager> implements DebtorLedgerManager {
}

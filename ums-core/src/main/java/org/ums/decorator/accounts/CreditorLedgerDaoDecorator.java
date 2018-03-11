package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.CreditorLedger;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;
import org.ums.manager.accounts.CreditorLedgerManager;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class CreditorLedgerDaoDecorator extends
    ContentDaoDecorator<CreditorLedger, MutableCreditorLedger, Long, CreditorLedgerManager> implements
    CreditorLedgerManager {
}

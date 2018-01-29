package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Transaction;
import org.ums.domain.model.mutable.accounts.MutableTransaction;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface TransactionManager
    extends
    ContentManager<Transaction, MutableTransaction, Long> {
}
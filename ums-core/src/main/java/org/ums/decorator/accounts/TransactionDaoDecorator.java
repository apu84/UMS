package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Transaction;
import org.ums.domain.model.mutable.accounts.MutableTransaction;
import org.ums.manager.accounts.TransactionManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class TransactionDaoDecorator extends
    ContentDaoDecorator<Transaction, MutableTransaction, Long, TransactionManager> implements TransactionManager {
}

package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.manager.accounts.ReceiptManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class ReceiptDaoDecorator extends ContentDaoDecorator<Receipt, MutableReceipt, Long, ReceiptManager> implements
    ReceiptManager {
}

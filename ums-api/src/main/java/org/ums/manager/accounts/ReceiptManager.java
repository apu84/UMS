package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface ReceiptManager
    extends
    ContentManager<Receipt, MutableReceipt, Long> {
}
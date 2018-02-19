package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableVoucher;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public interface VoucherManager extends ContentManager<Voucher, MutableVoucher, Long> {
}

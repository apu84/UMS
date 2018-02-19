package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableVoucher;
import org.ums.manager.accounts.VoucherManager;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class VoucherDaoDecorator extends ContentDaoDecorator<Voucher, MutableVoucher, Long, VoucherManager> implements
    VoucherManager {
}

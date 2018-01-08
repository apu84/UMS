package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.manager.accounts.VoucherNumberControlManager;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class VoucherNumberControlDaoDecorator extends
    ContentDaoDecorator<VoucherNumberControl, MutableVoucherNumberControl, Long, VoucherNumberControlManager> implements
    VoucherNumberControlManager {
}

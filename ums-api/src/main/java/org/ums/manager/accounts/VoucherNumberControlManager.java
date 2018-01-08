package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public interface VoucherNumberControlManager extends
    ContentManager<VoucherNumberControl, MutableVoucherNumberControl, Long> {

}

package org.ums.accounts.resource.definitions.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.manager.accounts.VoucherManager;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class MutableVoucherResource extends Resource {
  @Autowired
  protected VoucherManager mVoucherManager;
}

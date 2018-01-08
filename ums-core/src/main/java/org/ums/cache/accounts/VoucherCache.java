package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableVoucher;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.VoucherManager;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class VoucherCache extends ContentCache<Voucher, MutableVoucher, Long, VoucherManager> implements VoucherManager {
  CacheManager<Voucher, Long> mVoucherLongCacheManager;

  public VoucherCache(CacheManager<Voucher, Long> pVoucherLongCacheManager) {
    mVoucherLongCacheManager = pVoucherLongCacheManager;
  }

  @Override
  protected CacheManager<Voucher, Long> getCacheManager() {
    return mVoucherLongCacheManager;
  }
}

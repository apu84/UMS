package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.ReceiptManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class ReceiptCache extends ContentCache<Receipt, MutableReceipt, Long, ReceiptManager> implements ReceiptManager {
  private CacheManager<Receipt, Long> mReceiptLongCacheManager;

  public ReceiptCache(CacheManager<Receipt, Long> pReceiptLongCacheManager) {
    mReceiptLongCacheManager = pReceiptLongCacheManager;
  }

  @Override
  protected CacheManager<Receipt, Long> getCacheManager() {
    return mReceiptLongCacheManager;
  }
}

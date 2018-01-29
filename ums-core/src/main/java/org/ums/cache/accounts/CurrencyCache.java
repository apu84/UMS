package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.CurrencyManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class CurrencyCache extends ContentCache<Currency, MutableCurrency, Long, CurrencyManager> implements CurrencyManager {

  CacheManager<Currency, Long> mCurrencyLongCacheManager;

  public CurrencyCache(CacheManager<Currency, Long> pCurrencyLongCacheManager) {
    mCurrencyLongCacheManager = pCurrencyLongCacheManager;
  }

  @Override
  protected CacheManager<Currency, Long> getCacheManager() {
    return mCurrencyLongCacheManager;
  }
}

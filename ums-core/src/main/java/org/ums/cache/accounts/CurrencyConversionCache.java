package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.CurrencyConversionManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class CurrencyConversionCache extends
    ContentCache<CurrencyConversion, MutableCurrencyConversion, Long, CurrencyConversionManager> implements
    CurrencyConversionManager {
  private CacheManager<CurrencyConversion, Long> mCurrencyConversionLongCacheManager;

  public CurrencyConversionCache(CacheManager<CurrencyConversion, Long> pCurrencyConversionLongCacheManager) {
    mCurrencyConversionLongCacheManager = pCurrencyConversionLongCacheManager;
  }

  @Override
  protected CacheManager<CurrencyConversion, Long> getCacheManager() {
    return mCurrencyConversionLongCacheManager;
  }
}

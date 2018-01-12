package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.PredefinedNarrationManager;

/**
 * Created by Monjur-E-Morshed on 12-Jan-18.
 */
public class PredefinedNarrationCache extends ContentCache<PredefinedNarration, MutablePredefinedNarration, Long, PredefinedNarrationManager> implements PredefinedNarrationManager {
  private CacheManager<PredefinedNarration, Long> mPredefinedNarrationLongCacheManager;

  public PredefinedNarrationCache(CacheManager<PredefinedNarration, Long> pPredefinedNarrationLongCacheManager) {
    mPredefinedNarrationLongCacheManager = pPredefinedNarrationLongCacheManager;
  }

  @Override
  protected CacheManager<PredefinedNarration, Long> getCacheManager() {
    return mPredefinedNarrationLongCacheManager;
  }
}

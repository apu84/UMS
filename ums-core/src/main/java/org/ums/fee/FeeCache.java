package org.ums.fee;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

public class FeeCache extends ContentCache<Fee, MutableFee, Integer, FeeManager> implements
    FeeManager {
  private CacheManager<Fee, Integer> mCacheManager;

  public FeeCache(final CacheManager<Fee, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Fee, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(FeeCategory.class, pId);
  }
}

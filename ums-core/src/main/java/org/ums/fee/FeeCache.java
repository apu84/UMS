package org.ums.fee;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

public class FeeCache extends ContentCache<Fee, MutableFee, Long, FeeManager> implements FeeManager {
  private CacheManager<Fee, Long> mCacheManager;

  public FeeCache(final CacheManager<Fee, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Fee, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Long pId) {
    return CacheUtil.getCacheKey(FeeCategory.class, pId);
  }
}

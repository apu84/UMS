package org.ums.fee;

import java.util.List;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

public class FeeCategoryCache extends ContentCache<FeeCategory, MutableFeeCategory, String, FeeCategoryManager>
    implements FeeCategoryManager {

  private CacheManager<FeeCategory, String> mCacheManager;

  public FeeCategoryCache(final CacheManager<FeeCategory, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<FeeCategory, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(FeeCategory.class, pId);
  }

  @Override
  public List<FeeCategory> getFeeCategories(Integer pFeeTypeId) {
    String cacheKey = getCacheKey(FeeCategory.class.toString(), pFeeTypeId);
    return cachedList(cacheKey, () -> getManager().getFeeCategories(pFeeTypeId));
  }

  @Override
  public FeeCategory getByFeeId(String pFeeId) {
    String cacheKey = getCacheKey(FeeCategory.class.toString(), pFeeId);
    return cachedEntity(cacheKey, () -> getManager().getByFeeId(pFeeId));
  }

  @Override
  protected String getCachedListKey() {
    return "FeeCategoryCacheList";
  }
}

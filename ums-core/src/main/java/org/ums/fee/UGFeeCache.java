package org.ums.fee;

import java.util.List;
import java.util.stream.Collectors;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

public class UGFeeCache extends ContentCache<UGFee, MutableUGFee, Long, UGFeeManager> implements UGFeeManager {
  private CacheManager<UGFee, Long> mCacheManager;

  public UGFeeCache(final CacheManager<UGFee, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<UGFee, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Long pId) {
    return CacheUtil.getCacheKey(UGFee.class, pId);
  }

  @Override
  public List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId) {
    String cacheKey = getCacheKey(UGFee.class.toString(), pFacultyId, pSemesterId);
    return cachedList(cacheKey, () -> getManager().getFee(pFacultyId, pSemesterId));
  }

  @Override
  public List<UGFee> getFee(Integer pFacultyId, Integer pSemesterId, List<FeeCategory> pCategories) {
    String cacheKey = getCacheKey(UGFee.class.toString(), pFacultyId, pSemesterId,
        pCategories.stream().map(FeeCategory::getFeeId).collect(Collectors.joining("-")));
    return cachedList(cacheKey, () -> getManager().getFee(pFacultyId, pSemesterId, pCategories));
  }

  @Override
  public List<UGFee> getLatestFee(Integer pFacultyId, Integer pSemesterId) {
    String cacheKey = getCacheKey(UGFee.class.toString(), pFacultyId, pSemesterId);
    return cachedList(cacheKey, () -> getManager().getLatestFee(pFacultyId, pSemesterId));
  }

  @Override
  public List<Integer> getDistinctSemesterIds(Integer pFacultyId) {
    return getManager().getDistinctSemesterIds(pFacultyId);
  }

  @Override
  protected String getCachedListKey() {
    return "UGFeeCacheList";
  }
}

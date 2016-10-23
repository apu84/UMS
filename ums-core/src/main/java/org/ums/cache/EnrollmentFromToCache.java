package org.ums.cache;

import org.ums.domain.model.immutable.EnrollmentFromTo;
import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.manager.CacheManager;
import org.ums.manager.EnrollmentFromToManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class EnrollmentFromToCache extends
    ContentCache<EnrollmentFromTo, MutableEnrollmentFromTo, Integer, EnrollmentFromToManager>
    implements EnrollmentFromToManager {
  private CacheManager<EnrollmentFromTo, Integer> mCacheManager;

  public EnrollmentFromToCache(final CacheManager<EnrollmentFromTo, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EnrollmentFromTo, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(EnrollmentFromTo.class, pId);
  }

  @Override
  public List<EnrollmentFromTo> getEnrollmentFromTo(Integer pProgramId) {
    return getManager().getEnrollmentFromTo(pProgramId);
  }
}

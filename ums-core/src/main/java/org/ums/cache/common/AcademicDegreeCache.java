package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;
import org.ums.manager.CacheManager;
import org.ums.manager.common.AcademicDegreeManager;

public class AcademicDegreeCache extends
    ContentCache<AcademicDegree, MutableAcademicDegree, Integer, AcademicDegreeManager> implements
    AcademicDegreeManager {

  CacheManager<AcademicDegree, Integer> mCacheManager;

  public AcademicDegreeCache(CacheManager<AcademicDegree, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AcademicDegree, Integer> getCacheManager() {
    return mCacheManager;
  }
}

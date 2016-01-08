package org.ums.academic.dao;


import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.regular.Semester;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

public class SemesterCache extends ContentCache<Semester, MutableSemester, Integer> {
  private CacheManager<Semester> mCacheManager;

  public SemesterCache(final CacheManager<Semester> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Semester.class, pId);
  }
}

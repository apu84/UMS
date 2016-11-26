package org.ums.cache;

import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.mutable.MutableEquivalentCourse;
import org.ums.manager.CacheManager;
import org.ums.manager.EquivalentCourseManager;
import org.ums.util.CacheUtil;

public class EquivalentCourseCache extends
    ContentCache<EquivalentCourse, MutableEquivalentCourse, Integer, EquivalentCourseManager>
    implements EquivalentCourseManager {

  private CacheManager<EquivalentCourse, Integer> mCacheManager;

  public EquivalentCourseCache(final CacheManager<EquivalentCourse, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EquivalentCourse, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(EquivalentCourse.class, pId);
  }
}

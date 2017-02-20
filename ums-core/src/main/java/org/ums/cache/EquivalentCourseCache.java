package org.ums.cache;

import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.mutable.MutableEquivalentCourse;
import org.ums.manager.CacheManager;
import org.ums.manager.EquivalentCourseManager;
import org.ums.util.CacheUtil;

public class EquivalentCourseCache extends
    ContentCache<EquivalentCourse, MutableEquivalentCourse, Long, EquivalentCourseManager>
    implements EquivalentCourseManager {

  private CacheManager<EquivalentCourse, Long> mCacheManager;

  public EquivalentCourseCache(final CacheManager<EquivalentCourse, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EquivalentCourse, Long> getCacheManager() {
    return mCacheManager;
  }
}

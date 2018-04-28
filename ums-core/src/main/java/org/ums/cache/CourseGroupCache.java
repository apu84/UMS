package org.ums.cache;

import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.CourseGroupManager;
import org.ums.util.CacheUtil;

public class CourseGroupCache extends ContentCache<CourseGroup, MutableCourseGroup, Integer, CourseGroupManager>
    implements CourseGroupManager {
  private CacheManager<CourseGroup, Integer> mCacheManager;

  public CourseGroupCache(final CacheManager<CourseGroup, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<CourseGroup, Integer> getCacheManager() {
    return mCacheManager;
  }

}

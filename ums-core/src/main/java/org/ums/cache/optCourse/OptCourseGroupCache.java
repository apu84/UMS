package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptCourseGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptCourseGroupManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptCourseGroupCache extends
    ContentCache<OptCourseGroup, MutableOptCourseGroup, Long, OptCourseGroupManager> implements OptCourseGroupManager {
  CacheManager<OptCourseGroup, Long> mCacheManager;

  @Override
  protected CacheManager<OptCourseGroup, Long> getCacheManager() {
    return mCacheManager;
  }

  public OptCourseGroupCache(CacheManager<OptCourseGroup, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}

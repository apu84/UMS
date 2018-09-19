package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptOfferedSubGroupCourseMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptOfferedSubGroupCourseMapCache
    extends
    ContentCache<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap, Long, OptOfferedSubGroupCourseMapManager>
    implements OptOfferedSubGroupCourseMapManager {
  CacheManager<OptOfferedSubGroupCourseMap, Long> mCacheManager;

  @Override
  protected CacheManager<OptOfferedSubGroupCourseMap, Long> getCacheManager() {
    return mCacheManager;
  }

  public OptOfferedSubGroupCourseMapCache(CacheManager<OptOfferedSubGroupCourseMap, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<OptOfferedSubGroupCourseMap> getSubGroupCourses() {
    return getManager().getSubGroupCourses();
  }
}

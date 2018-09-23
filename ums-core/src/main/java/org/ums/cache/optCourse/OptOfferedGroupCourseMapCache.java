package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptOfferedGroupCourseMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupCourseMapCache extends
    ContentCache<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap, Long, OptOfferedGroupCourseMapManager>
    implements OptOfferedGroupCourseMapManager {
  CacheManager<OptOfferedGroupCourseMap, Long> mCacheManager;

  @Override
  protected CacheManager<OptOfferedGroupCourseMap, Long> getCacheManager() {
    return mCacheManager;
  }

  public OptOfferedGroupCourseMapCache(CacheManager<OptOfferedGroupCourseMap, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<OptOfferedGroupCourseMap> getInfo(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return getManager().getInfo(pSemesterId, pProgramId, pYear, pSemester);
  }
}

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

  @Override
  public CourseGroup getBySyllabus(Integer pCourseGroupId, String pSyllabusId) throws Exception {
    return getManager().getBySyllabus(pCourseGroupId, pSyllabusId);
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(CourseGroup.class, pId);
  }
}
package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.optCourse.OptStudentCourseSelection;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptStudentCourseSelectionManager;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class OptStudentCourseSelectionCache extends
    ContentCache<OptStudentCourseSelection, MutableOptStudentCourseSelection, Long, OptStudentCourseSelectionManager>
    implements OptStudentCourseSelectionManager {
  CacheManager<OptStudentCourseSelection, Long> mCacheManager;

  public OptStudentCourseSelectionCache(CacheManager<OptStudentCourseSelection, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<OptStudentCourseSelection, Long> getCacheManager() {
    return mCacheManager;
  }
}

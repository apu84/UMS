package org.ums.cache;

import org.ums.domain.model.immutable.ApplicationTesSelectedCourses;
import org.ums.domain.model.mutable.MutableApplicationTesSelectedCourses;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;
import org.ums.manager.ApplicationTesSelectedCourseManager;
import org.ums.manager.CacheManager;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class ApplicationTesSelectedCoursesCache
    extends
    ContentCache<ApplicationTesSelectedCourses, MutableApplicationTesSelectedCourses, Long, ApplicationTesSelectedCourseManager>
    implements ApplicationTesSelectedCourseManager {
  CacheManager<ApplicationTesSelectedCourses, Long> mCacheManager;

  @Override
  protected CacheManager<ApplicationTesSelectedCourses, Long> getCacheManager() {
    return mCacheManager;
  }

  public ApplicationTesSelectedCoursesCache(CacheManager<ApplicationTesSelectedCourses, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}

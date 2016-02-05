package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.readOnly.Course;
import org.ums.manager.CacheManager;
import org.ums.manager.CourseManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by User on 1/16/2016.
 */
public class CourseCache extends ContentCache<Course, MutableCourse, String, CourseManager> implements CourseManager {
  CacheManager<Course> mCacheManager;

  public CourseCache(CacheManager<Course> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Course> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Course.class, pId);
  }

  @Override
  public List<Course> getBySyllabus(String pSyllabusId) {
    return getManager().getBySyllabus(pSyllabusId);
  }
}

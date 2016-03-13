package org.ums.cache;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.manager.CacheManager;
import org.ums.manager.StudentManager;
import org.ums.util.CacheUtil;

public class StudentCache extends ContentCache<Student, MutableStudent, String, StudentManager> implements StudentManager {
  private CacheManager<Student> mCacheManager;

  public StudentCache(final CacheManager<Student> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Student> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Student.class, pId);
  }

}
package org.ums.cache;

import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.manager.CacheManager;
import org.ums.manager.TeacherManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class TeacherCache extends ContentCache<Teacher, MutableTeacher, String, TeacherManager> implements TeacherManager {
  private CacheManager<Teacher> mCacheManager;

  public TeacherCache(final CacheManager<Teacher> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Teacher> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Teacher.class, pId);
  }

  @Override
  public List<Teacher> getByDepartment(Department pDepartment) {
    return getManager().getByDepartment(pDepartment);
  }
}
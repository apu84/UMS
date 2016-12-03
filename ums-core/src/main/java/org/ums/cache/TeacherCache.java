package org.ums.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.manager.CacheManager;
import org.ums.manager.TeacherManager;
import org.ums.util.CacheUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TeacherCache extends ContentCache<Teacher, MutableTeacher, String, TeacherManager>
    implements TeacherManager {
  private static final Logger mLogger = LoggerFactory.getLogger(TeacherCache.class);

  private CacheManager<Teacher, String> mCacheManager;

  public TeacherCache(final CacheManager<Teacher, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Teacher, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Teacher.class, pId);
  }

  @Override
  public List<Teacher> getByDepartment(Department pDepartment) {
    String cacheKey = getCacheKey(Teacher.class.toString(), pDepartment.getId());
    return cachedList(cacheKey, () -> getManager().getByDepartment(pDepartment));
  }

  @Override
  protected String getCachedListKey() {
    return "TeacherListCache";
  }

}

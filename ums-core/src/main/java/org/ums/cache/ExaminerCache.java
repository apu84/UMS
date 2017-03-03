package org.ums.cache;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.CacheManager;
import org.ums.manager.ExaminerManager;
import org.ums.util.CacheUtil;

public class ExaminerCache extends
    AssignedTeacherCache<Examiner, MutableExaminer, Long, ExaminerManager> implements
    ExaminerManager {
  public ExaminerCache(final CacheManager<Examiner, Long> pCacheManager) {
    super(pCacheManager);
  }

  @Override
  protected String getCacheKey(Long pId) {
    return CacheUtil.getCacheKey(Examiner.class, pId);
  }

}

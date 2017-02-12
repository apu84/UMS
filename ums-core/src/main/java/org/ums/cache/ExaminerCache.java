package org.ums.cache;

import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.CacheManager;

public class ExaminerCache
    extends
    AssignedTeacherCache<Examiner, MutableExaminer, Long, AssignedTeacherManager<Examiner, MutableExaminer, Long>> {
  public ExaminerCache(final CacheManager<Examiner, Long> pCacheManager) {
    super(pCacheManager);
  }
}

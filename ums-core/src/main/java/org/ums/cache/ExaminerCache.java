package org.ums.cache;


import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.CacheManager;

public class ExaminerCache extends AssignedTeacherCache<Examiner, MutableExaminer, Integer> {
  public ExaminerCache(final CacheManager<Examiner, Integer> pCacheManager) {
    super(pCacheManager);
  }
}

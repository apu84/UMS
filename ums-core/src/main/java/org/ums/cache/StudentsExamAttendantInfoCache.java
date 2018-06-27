package org.ums.cache;

import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.manager.CacheManager;
import org.ums.manager.StudentsExamAttendantInfoManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public class StudentsExamAttendantInfoCache extends
    ContentCache<StudentsExamAttendantInfo, MutableStudentsExamAttendantInfo, Long, StudentsExamAttendantInfoManager>
    implements StudentsExamAttendantInfoManager {
  CacheManager<StudentsExamAttendantInfo, Long> mCacheManager;

  public StudentsExamAttendantInfoCache(CacheManager<StudentsExamAttendantInfo, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<StudentsExamAttendantInfo, Long> getCacheManager() {
    return null;
  }

  @Override
  public List<StudentsExamAttendantInfo> getSemesterExamTypeDateWiseRecords(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    return getManager().getSemesterExamTypeDateWiseRecords(pSemesterId, pExamType, pExamDate);
  }
}

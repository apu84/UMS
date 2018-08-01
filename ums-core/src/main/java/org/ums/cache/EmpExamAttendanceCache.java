package org.ums.cache;

import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.manager.CacheManager;
import org.ums.manager.EmpExamAttendanceManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamAttendanceCache extends
    ContentCache<EmpExamAttendance, MutableEmpExamAttendance, Long, EmpExamAttendanceManager> implements
    EmpExamAttendanceManager {
  CacheManager<EmpExamAttendance, Long> mCacheManager;

  public EmpExamAttendanceCache(CacheManager<EmpExamAttendance, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EmpExamAttendance, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<EmpExamAttendance> getInfoBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return getManager().getInfoBySemesterAndExamType(pSemesterId, pExamType);
  }

  @Override
  public List<EmpExamAttendance> getInfoByInvigilatorDate(Integer pSemesterId, Integer pExamType, String pExamDate) {
    return getManager().getInfoByInvigilatorDate(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public List<EmpExamAttendance> getInfoByReserveDate(Integer pSemesterId, Integer pExamType, String pExamDate) {
    return getManager().getInfoByReserveDate(pSemesterId, pExamType, pExamDate);
  }
}

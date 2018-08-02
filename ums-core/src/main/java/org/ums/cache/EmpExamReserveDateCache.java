package org.ums.cache;

import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.manager.CacheManager;
import org.ums.manager.EmpExamReserveDateManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamReserveDateCache extends
    ContentCache<EmpExamReserveDate, MutableEmpExamReserveDate, Long, EmpExamReserveDateManager> implements
    EmpExamReserveDateManager {
  CacheManager<EmpExamReserveDate, Long> mCacheManager;

  @Override
  protected CacheManager<EmpExamReserveDate, Long> getCacheManager() {
    return mCacheManager;
  }

  public EmpExamReserveDateCache(CacheManager<EmpExamReserveDate, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<EmpExamReserveDate> getBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return getManager().getBySemesterAndExamType(pSemesterId, pExamType);
  }
}

package org.ums.cache;

import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterWithdrawalLogManager;
import org.ums.util.CacheUtil;


public class SemesterWithdrawalLogCache extends ContentCache<SemesterWithdrawalLog,MutableSemesterWithdrawalLog,Integer,SemesterWithdrawalLogManager> implements SemesterWithdrawalLogManager {

  CacheManager<SemesterWithdrawalLog> mCacheManager;

  public SemesterWithdrawalLogCache(CacheManager<SemesterWithdrawalLog> pCachemanager){
    mCacheManager = pCachemanager;
  }

  @Override
  public SemesterWithdrawalLog getForStudent(String pStudentId,int semesterId) {
    return getManager().getForStudent(pStudentId,semesterId);
  }

  @Override
  protected CacheManager<SemesterWithdrawalLog> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SemesterWithdrawalLog.class, pId);
  }
}

package org.ums.cache;

import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableSubGroupCCI;
import org.ums.manager.CacheManager;
import org.ums.manager.SubGroupCCIManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 7/23/2016.
 */
public class SubGroupCCICache extends
    ContentCache<SubGroupCCI, MutableSubGroupCCI, Integer, SubGroupCCIManager> implements
    SubGroupCCIManager {

  CacheManager<SubGroupCCI, Integer> mCacheManager;

  public SubGroupCCICache(CacheManager<SubGroupCCI, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SubGroupCCI, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SubGroupCCI.class, pId);
  }

  @Override
  public List<SubGroupCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    return getManager().getBySemesterAndExamDate(pSemesterId, pExamDate);
  }

  @Override
  public Integer checkOccuranceBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    return getManager().checkOccuranceBySemesterAndExamDate(pSemesterId, pExamDate);
  }

  @Override
  public Integer deleteBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    return getManager().deleteBySemesterAndExamDate(pSemesterId, pExamDate);
  }

  @Override
  public Integer checkSubGroupNumber(Integer pSemesterId, String pExamDate) {
    return getManager().checkSubGroupNumber(pSemesterId, pExamDate);
  }

  @Override
  public Integer checkForHalfFinishedSubGroup(Integer pSemesterId, String pExamDate) {
    return getManager().checkForHalfFinishedSubGroup(pSemesterId, pExamDate);
  }
}

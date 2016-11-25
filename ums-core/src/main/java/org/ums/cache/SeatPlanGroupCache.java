package org.ums.cache;

import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.SeatPlanGroupManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by Munna on 4/20/2016.
 */
public class SeatPlanGroupCache extends
    ContentCache<SeatPlanGroup, MutableSeatPlanGroup, Integer, SeatPlanGroupManager> implements
    SeatPlanGroupManager {

  CacheManager<SeatPlanGroup, Integer> mCacheManager;

  public SeatPlanGroupCache(CacheManager<SeatPlanGroup, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SeatPlanGroup, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SeatPlanGroup.class, pId);
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemester(int pSemesterId, int pExamType) {
    return getManager().getGroupBySemester(pSemesterId, pExamType);
  }

  @Override
  public List<SeatPlanGroup> getBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().getBySemesterGroupAndType(pSemesterId, pGroupNo, pType);
  }

  @Override
  public int deleteBySemesterAndExamType(int pSemesterId, int pExamType) {
    return getManager().deleteBySemesterAndExamType(pSemesterId, pExamType);
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemesterTypeFromDb(int pSemesterId, int pExamType) {
    return getManager().getGroupBySemesterTypeFromDb(pSemesterId, pExamType);
  }

  @Override
  public int checkSeatPlanGroupDataSize(int pSemesterId, int pExamType) {
    return getManager().checkSeatPlanGroupDataSize(pSemesterId, pExamType);
  }

  @Override
  public int createSeatPlanGroup(int pSemesterid, int pExamType) {
    return getManager().createSeatPlanGroup(pSemesterid, pExamType);
  }
}

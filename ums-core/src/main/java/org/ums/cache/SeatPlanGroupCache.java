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
public class SeatPlanGroupCache extends ContentCache<SeatPlanGroup,MutableSeatPlanGroup,Integer,SeatPlanGroupManager> implements SeatPlanGroupManager {

  CacheManager<SeatPlanGroup> mCacheManager;

  public SeatPlanGroupCache(CacheManager<SeatPlanGroup> pCacheManager){
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SeatPlanGroup> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SeatPlanGroup.class,pId);
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemester(int pSemesterId,int pExamType) {
    return getManager().getGroupBySemester(pSemesterId,pExamType);
  }
}

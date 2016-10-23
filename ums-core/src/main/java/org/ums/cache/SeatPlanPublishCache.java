package org.ums.cache;

import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.manager.CacheManager;
import org.ums.manager.SeatPlanPublishManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 8/2/2016.
 */
public class SeatPlanPublishCache extends
    ContentCache<SeatPlanPublish, MutableSeatPlanPublish, Integer, SeatPlanPublishManager>
    implements SeatPlanPublishManager {
  private CacheManager<SeatPlanPublish, Integer> mCacheManager;

  public SeatPlanPublishCache(final CacheManager<SeatPlanPublish, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SeatPlanPublish, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SeatPlanPublish.class, pId);
  }

  @Override
  public List<SeatPlanPublish> getBySemester(Integer pSemesterId) {
    return getManager().getBySemester(pSemesterId);
  }

  @Override
  public Integer checkBySemester(Integer pSemesterId) {
    return getManager().checkBySemester(pSemesterId);
  }

  @Override
  public Integer deleteBySemester(Integer pSemesterId) {
    return getManager().deleteBySemester(pSemesterId);
  }
}

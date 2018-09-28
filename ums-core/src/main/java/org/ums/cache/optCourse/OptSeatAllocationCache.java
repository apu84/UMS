package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptSeatAllocationManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class OptSeatAllocationCache extends
    ContentCache<OptSeatAllocation, MutableOptSeatAllocation, Long, OptSeatAllocationManager> implements
    OptSeatAllocationManager {
  CacheManager<OptSeatAllocation, Long> mCacheManager;

  public OptSeatAllocationCache(CacheManager<OptSeatAllocation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<OptSeatAllocation, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<OptSeatAllocation> getInfoBySemesterId(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return getManager().getInfoBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
  }
}

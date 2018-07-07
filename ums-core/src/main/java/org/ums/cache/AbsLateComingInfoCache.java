package org.ums.cache;

import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.manager.AbsLateComingInfoManager;
import org.ums.manager.CacheManager;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class AbsLateComingInfoCache extends
    ContentCache<AbsLateComingInfo, MutableAbsLateComingInfo, Long, AbsLateComingInfoManager> implements
    AbsLateComingInfoManager {
  CacheManager<AbsLateComingInfo, Long> mCacheManager;

  public AbsLateComingInfoCache(CacheManager<AbsLateComingInfo, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AbsLateComingInfo, Long> getCacheManager() {
    return null;
  }
}

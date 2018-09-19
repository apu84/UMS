package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptOfferedGroupSubGroupMapManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupSubGroupMapCache
    extends
    ContentCache<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long, OptOfferedGroupSubGroupMapManager>
    implements OptOfferedGroupSubGroupMapManager {
  CacheManager<OptOfferedGroupSubGroupMap, Long> mCacheManager;

  @Override
  protected CacheManager<OptOfferedGroupSubGroupMap, Long> getCacheManager() {
    return mCacheManager;
  }

  public OptOfferedGroupSubGroupMapCache(CacheManager<OptOfferedGroupSubGroupMap, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}

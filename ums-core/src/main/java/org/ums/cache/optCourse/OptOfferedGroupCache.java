package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptOfferedGroupManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupCache extends
    ContentCache<OptOfferedGroup, MutableOptOfferedGroup, Long, OptOfferedGroupManager> implements
    OptOfferedGroupManager {
  CacheManager<OptOfferedGroup, Long> mCacheManager;

  @Override
  protected CacheManager<OptOfferedGroup, Long> getCacheManager() {
    return mCacheManager;
  }

  public OptOfferedGroupCache(CacheManager<OptOfferedGroup, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}

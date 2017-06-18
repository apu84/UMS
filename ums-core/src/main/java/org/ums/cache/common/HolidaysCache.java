package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.mutable.common.MutableHolidays;
import org.ums.manager.CacheManager;
import org.ums.manager.common.HolidaysManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class HolidaysCache extends ContentCache<Holidays, MutableHolidays, Long, HolidaysManager> implements
    HolidaysManager {

  private CacheManager<Holidays, Long> mCacheManager;

  public HolidaysCache(CacheManager<Holidays, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Holidays, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Holidays> getHolidays(int pYear) {
    return getManager().getHolidays(pYear);
  }
}

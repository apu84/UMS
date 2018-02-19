package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.CacheManager;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.RecordManager;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class RecordCache extends ContentCache<Record, MutableRecord, Long, RecordManager> implements RecordManager {

  CacheManager<Record, Long> mCacheManager;

  public RecordCache(CacheManager<Record, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Record, Long> getCacheManager() {
    return mCacheManager;
  }

}

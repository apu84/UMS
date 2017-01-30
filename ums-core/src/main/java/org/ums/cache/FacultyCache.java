package org.ums.cache;

import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.mutable.MutableFaculty;
import org.ums.manager.CacheManager;
import org.ums.manager.FacultyManager;
import org.ums.util.CacheUtil;

/**
 * Created by Monjur-E-Morshed on 06-Dec-16.
 */
public class FacultyCache extends ContentCache<Faculty, MutableFaculty, Integer, FacultyManager>
    implements FacultyManager {

  private CacheManager<Faculty, Integer> mCacheManager;

  public FacultyCache(final CacheManager<Faculty, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Faculty, Integer> getCacheManager() {
    return mCacheManager;
  }

}

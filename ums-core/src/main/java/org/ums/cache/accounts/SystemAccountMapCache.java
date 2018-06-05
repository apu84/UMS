package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.SystemAccountMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public class SystemAccountMapCache extends
    ContentCache<SystemAccountMap, MutableSystemAccountMap, Long, SystemAccountMapManager> implements
    SystemAccountMapManager {
  private CacheManager<SystemAccountMap, Long> mCacheManager;

  public SystemAccountMapCache(CacheManager<SystemAccountMap, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SystemAccountMap, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<SystemAccountMap> getAll(Company pCompany) {
    return getManager().getAll(pCompany);
  }
}

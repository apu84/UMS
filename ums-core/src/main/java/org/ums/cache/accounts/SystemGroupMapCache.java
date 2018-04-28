package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.SystemGroupMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public class SystemGroupMapCache extends
    ContentCache<SystemGroupMap, MutableSystemGroupMap, String, SystemGroupMapManager> implements SystemGroupMapManager {

  private CacheManager<SystemGroupMap, String> mSystemGroupMapLongCacheManager;

  public SystemGroupMapCache(CacheManager<SystemGroupMap, String> pSystemGroupMapLongCacheManager) {
    mSystemGroupMapLongCacheManager = pSystemGroupMapLongCacheManager;
  }

  @Override
  protected CacheManager<SystemGroupMap, String> getCacheManager() {
    return mSystemGroupMapLongCacheManager;
  }

  @Override
  public List<SystemGroupMap> getAllByCompany(Company pCompany) {
    return getManager().getAllByCompany(pCompany);
  }
}

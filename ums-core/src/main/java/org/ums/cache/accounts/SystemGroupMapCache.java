package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.enums.accounts.definitions.group.GroupType;
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
  public SystemGroupMap get(GroupType pGroupType, Company pCompany) {
    return getManager().get(pGroupType, pCompany);
  }

  @Override
  public boolean exists(GroupType pGroupType, Company pCompany) {
    return getManager().exists(pGroupType, pCompany);
  }

  @Override
  protected CacheManager<SystemGroupMap, String> getCacheManager() {
    return mSystemGroupMapLongCacheManager;
  }

  @Override
  public List<SystemGroupMap> getAllByCompany(Company pCompany) {
    return getManager().getAllByCompany(pCompany);
  }

  @Override
  public int delete(Group pGroup, Company pCompany) {
    return getManager().delete(pGroup, pCompany);
  }
}

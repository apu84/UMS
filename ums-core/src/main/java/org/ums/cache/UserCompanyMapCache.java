package org.ums.cache;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.UserCompanyMap;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.domain.model.mutable.MutableUserCompanyMap;
import org.ums.manager.CacheManager;
import org.ums.manager.CompanyManager;
import org.ums.manager.UserCompanyMapManager;

import java.util.List;

public class UserCompanyMapCache extends
    ContentCache<UserCompanyMap, MutableUserCompanyMap, Long, UserCompanyMapManager> implements UserCompanyMapManager {

  private CacheManager<UserCompanyMap, Long> mUserCompanyMapCacheManager;

  public UserCompanyMapCache(CacheManager<UserCompanyMap, Long> pUserCompanyMapCacheManager) {
    mUserCompanyMapCacheManager = pUserCompanyMapCacheManager;
  }

  @Override
  protected CacheManager<UserCompanyMap, Long> getCacheManager() {
    return mUserCompanyMapCacheManager;
  }

  @Override
  public List<UserCompanyMap> getCompanyList(String pUserId) {
    return getManager().getCompanyList(pUserId);
  }
}

package org.ums.cache;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.manager.CacheManager;
import org.ums.manager.CompanyManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class CompanyCache extends ContentCache<Company, MutableCompany, Long, CompanyManager> implements CompanyManager {

  private CacheManager<Company, Long> mCompanyLongCacheManager;

  public CompanyCache(CacheManager<Company, Long> pCompanyLongCacheManager) {
    mCompanyLongCacheManager = pCompanyLongCacheManager;
  }

  @Override
  protected CacheManager<Company, Long> getCacheManager() {
    return mCompanyLongCacheManager;
  }
}

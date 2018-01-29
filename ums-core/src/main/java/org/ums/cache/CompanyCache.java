package org.ums.cache;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.manager.CacheManager;
import org.ums.manager.CompanyManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class CompanyCache extends ContentCache<Company, MutableCompany, String, CompanyManager> implements
    CompanyManager {

  private CacheManager<Company, String> mCompanyLongCacheManager;

  public CompanyCache(CacheManager<Company, String> pCompanyLongCacheManager) {
    mCompanyLongCacheManager = pCompanyLongCacheManager;
  }

  @Override
  protected CacheManager<Company, String> getCacheManager() {
    return mCompanyLongCacheManager;
  }
}

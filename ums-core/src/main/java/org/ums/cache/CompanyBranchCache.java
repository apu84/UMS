package org.ums.cache;

import org.ums.domain.model.immutable.CompanyBranch;
import org.ums.domain.model.mutable.MutableCompanyBranch;
import org.ums.manager.CacheManager;
import org.ums.manager.CompanyBranchManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class CompanyBranchCache extends ContentCache<CompanyBranch, MutableCompanyBranch, Long, CompanyBranchManager>
    implements CompanyBranchManager {

  private CacheManager<CompanyBranch, Long> mCompanyBranchLongCacheManager;

  public CompanyBranchCache(CacheManager<CompanyBranch, Long> pCompanyBranchLongCacheManager) {
    mCompanyBranchLongCacheManager = pCompanyBranchLongCacheManager;
  }

  @Override
  protected CacheManager<CompanyBranch, Long> getCacheManager() {
    return mCompanyBranchLongCacheManager;
  }
}

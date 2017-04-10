package org.ums.cache.registrar.employee;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.employee.AwardInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableAwardInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.employee.AwardInformationManager;

import java.util.List;

public class AwardInformationCache extends
    ContentCache<AwardInformation, MutableAwardInformation, Integer, AwardInformationManager> implements
    AwardInformationManager {

  private CacheManager<AwardInformation, Integer> mCacheManager;

  public AwardInformationCache(CacheManager<AwardInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AwardInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveAwardInformation(MutableAwardInformation pMutableAwardInformation) {
    return getManager().saveAwardInformation(pMutableAwardInformation);
  }

  @Override
  public List<AwardInformation> getEmployeeAwardInformation(int pEmployeeId) {
    return getManager().getEmployeeAwardInformation(pEmployeeId);
  }
}

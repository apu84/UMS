package org.ums.cache.registrar.employee;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableEmployeeInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.employee.EmployeeInformationManager;

public class EmployeeInformationCache extends
    ContentCache<EmployeeInformation, MutableEmployeeInformation, Integer, EmployeeInformationManager> implements
    EmployeeInformationManager {

  private CacheManager<EmployeeInformation, Integer> mCacheManager;

  public EmployeeInformationCache(CacheManager<EmployeeInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EmployeeInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveEmployeeInformation(MutableEmployeeInformation pMutableEmployeeInformation) {
    return getManager().saveEmployeeInformation(pMutableEmployeeInformation);
  }

  @Override
  public EmployeeInformation getEmployeeInformation(int pEmployeeId) {
    return getManager().getEmployeeInformation(pEmployeeId);
  }
}

package org.ums.cache.registrar.Employee;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.Employee.AcademicInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableAcademicInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.Employee.AcademicInformationManager;

import java.util.List;

public class AcademicInformationCache extends
    ContentCache<AcademicInformation, MutableAcademicInformation, Integer, AcademicInformationManager> implements
    AcademicInformationManager {

  private CacheManager<AcademicInformation, Integer> mCacheManager;

  public AcademicInformationCache(CacheManager<AcademicInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AcademicInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveAcademicInformation(MutableAcademicInformation pMutableAcademicInformation) {
    return getManager().saveAcademicInformation(pMutableAcademicInformation);
  }

  @Override
  public List<AcademicInformation> getEmployeeAcademicInformation(int pEmployeeId) {
    return getManager().getEmployeeAcademicInformation(pEmployeeId);
  }
}

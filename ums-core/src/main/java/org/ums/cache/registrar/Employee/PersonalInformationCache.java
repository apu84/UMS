package org.ums.cache.registrar.Employee;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.Employee.PersonalInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePersonalInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.Employee.PersonalInformationManager;

public class PersonalInformationCache extends
    ContentCache<PersonalInformation, MutablePersonalInformation, Integer, PersonalInformationManager> implements
    PersonalInformationManager {

  private CacheManager<PersonalInformation, Integer> mCacheManager;

  public PersonalInformationCache(CacheManager<PersonalInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<PersonalInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int savePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().savePersonalInformation(pMutablePersonalInformation);
  }

  @Override
  public PersonalInformation getEmployeePersonalInformation(int pEmployeeId) {
    return getManager().getEmployeePersonalInformation(pEmployeeId);
  }
}

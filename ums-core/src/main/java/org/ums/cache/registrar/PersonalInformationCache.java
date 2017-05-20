package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.PersonalInformationManager;

public class PersonalInformationCache extends
    ContentCache<PersonalInformation, MutablePersonalInformation, String, PersonalInformationManager> implements
    PersonalInformationManager {

  private CacheManager<PersonalInformation, String> mCacheManager;

  public PersonalInformationCache(CacheManager<PersonalInformation, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<PersonalInformation, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int savePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().savePersonalInformation(pMutablePersonalInformation);
  }

  @Override
  public PersonalInformation getEmployeePersonalInformation(String pEmployeeId) {
    return getManager().getEmployeePersonalInformation(pEmployeeId);
  }

  @Override
  public int deletePersonalInformation(String pEmployeeId) {
    return getManager().deletePersonalInformation(pEmployeeId);
  }

  @Override
  public int updatePersonalInformation(MutablePersonalInformation pMutablePersonalInformation) {
    return getManager().updatePersonalInformation(pMutablePersonalInformation);
  }
}

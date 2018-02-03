package org.ums.employee.personal;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

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
  /*
   * @Override public int savePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { return 0; }
   * 
   * @Override public PersonalInformation getPersonalInformation(String pEmployeeId) { return null;
   * }
   * 
   * @Override public int updatePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { return 0; }
   * 
   * @Override public int deletePersonalInformation(MutablePersonalInformation
   * pMutablePersonalInformation) { return 0; }
   */
}

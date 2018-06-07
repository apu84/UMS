package org.ums.employee.personal;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

import java.util.Optional;

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
  public Optional<PersonalInformation> getByEmail(String pEmail) {
    return getManager().getByEmail(pEmail);
  }
}

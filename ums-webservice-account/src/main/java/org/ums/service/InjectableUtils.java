package org.ums.service;

import org.springframework.context.ApplicationContext;
import org.ums.cache.CacheFactory;
import org.ums.context.AppContext;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.manager.CacheManager;
import org.ums.manager.CompanyManager;

public class InjectableUtils {
  private static CacheFactory mCacheFactory;
  private static CompanyManager sCompanyManager;
  private static PersonalInformationManager sPersonalInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    mCacheFactory = applicationContext.getBean("cacheFactory", CacheFactory.class);
  }

  public static CacheManager getCacheManager() {
    return mCacheFactory.getCacheManager();
  }

}

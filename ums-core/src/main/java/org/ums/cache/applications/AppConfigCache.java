package org.ums.cache.applications;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.applications.AppConfig;
import org.ums.domain.model.mutable.applications.MutableAppConfig;
import org.ums.manager.CacheManager;
import org.ums.manager.applications.AppConfigManager;

/**
 * Created by Monjur-E-Morshed on 20-Sep-17.
 */
public class AppConfigCache extends ContentCache<AppConfig, MutableAppConfig, Long, AppConfigManager> implements
    AppConfigManager {

  CacheManager<AppConfig, Long> mCacheManager;

  public AppConfigCache(CacheManager<AppConfig, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AppConfig, Long> getCacheManager() {
    return mCacheManager;
  }
}

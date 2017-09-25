package org.ums.cache.applications;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.applications.AppRules;
import org.ums.domain.model.mutable.applications.MutableAppRules;
import org.ums.manager.CacheManager;
import org.ums.manager.applications.AppRulesManager;

/**
 * Created by Monjur-E-Morshed on 21-Sep-17.
 */
public class AppRulesCache extends ContentCache<AppRules, MutableAppRules, Long, AppRulesManager> implements AppRulesManager {

  CacheManager<AppRules, Long> mCacheManager;

  public AppRulesCache(CacheManager<AppRules, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AppRules, Long> getCacheManager() {
    return mCacheManager;
  }
}

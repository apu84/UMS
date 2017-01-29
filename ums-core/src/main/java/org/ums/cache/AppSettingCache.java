package org.ums.cache;

import org.ums.domain.model.immutable.AppSetting;
import org.ums.domain.model.mutable.MutableAppSetting;
import org.ums.manager.AppSettingManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

/**
 * Created by My Pc on 30-Aug-16.
 */
public class AppSettingCache extends
    ContentCache<AppSetting, MutableAppSetting, Integer, AppSettingManager> implements
    AppSettingManager {

  CacheManager<AppSetting, Integer> mCacheManager;

  public AppSettingCache(CacheManager<AppSetting, Integer> pAppSettingIntegerCacheManager) {
    mCacheManager = pAppSettingIntegerCacheManager;
  }

  @Override
  protected CacheManager<AppSetting, Integer> getCacheManager() {
    return mCacheManager;
  }

}

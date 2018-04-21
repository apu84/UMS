package org.ums.cache;

import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.CacheManager;
import org.ums.manager.FCMTokenManager;

public class FCMTokenCache extends ContentCache<FCMToken, MutableFCMToken, Long, FCMTokenManager> implements
    FCMTokenManager {

  CacheManager<FCMToken, Long> mCacheManager;

  public FCMTokenCache(CacheManager<FCMToken, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<FCMToken, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public FCMToken getFCMToken(String pUserId) {
    return getManager().getFCMToken(pUserId);
  }
}

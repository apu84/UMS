package org.ums.cache;

import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.CacheManager;
import org.ums.manager.FCMTokenManager;

public class FCMTokenCache {

  /*
   * public class FCMTokenCache extends ContentCache<FCMToken, MutableFCMToken, String,
   * FCMTokenManager> implements FCMTokenManager {
   * 
   * CacheManager<FCMToken, String> mCacheManager;
   * 
   * public FCMTokenCache(CacheManager<FCMToken, String> pCacheManager) { mCacheManager =
   * pCacheManager; }
   * 
   * @Override protected CacheManager<FCMToken, String> getCacheManager() { return mCacheManager; }
   * 
   * @Override public boolean hasDuplicate(String pFCMToken) { return
   * getManager().hasDuplicate(pFCMToken); }
   * 
   * @Override public FCMToken getId(String pFCMToken) { return getManager().getId(pFCMToken); }
   */
}
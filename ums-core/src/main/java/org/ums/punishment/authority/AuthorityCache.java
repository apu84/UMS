package org.ums.punishment.authority;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class AuthorityCache extends ContentCache<Authority, MutableAuthority, Long, AuthorityManager> implements
    AuthorityManager {

  CacheManager<Authority, Long> mCacheManager;

  public AuthorityCache(CacheManager<Authority, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Authority, Long> getCacheManager() {
    return mCacheManager;
  }
}

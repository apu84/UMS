package org.ums.cache;

import org.springframework.stereotype.Component;
import org.ums.manager.CacheManager;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocalCacheManager<R> implements CacheManager<R> {
  private Map<String, R> mCache;

  public LocalCacheManager() {
    mCache = new HashMap<>();
  }

  @Override
  public void put(String pCacheId, R pReadonly) {
    mCache.putIfAbsent(pCacheId, pReadonly);
  }

  @Override
  public R get(String pCacheId) throws Exception {
    return mCache.get(pCacheId);
  }

  @Override
  public void invalidate(String pCacheId) {
    mCache.remove(pCacheId);
  }
}

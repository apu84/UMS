package org.ums.cache;

import org.springframework.stereotype.Component;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocalCacheManager<R extends LastModifier, I> implements CacheManager<R, I> {
  private Map<String, R> mCache;
  private Map<String, List<I>> mCacheList;
  private Map<String, String> mLastModified;
  private Map<String, List<String>> mCachedKeyList;

  public LocalCacheManager() {
    mCache = new HashMap<>();
    mCacheList = new HashMap<>();
    mCachedKeyList = new HashMap<>();
    mLastModified = new HashMap<>();
  }

  @Override
  public void put(String pCacheId, R pReadonly) {
    mCache.put(pCacheId, pReadonly);
    mLastModified.put(pCacheId, pReadonly.getLastModified());
  }

  @Override
  public R get(String pCacheId) throws Exception {
    return mCache.get(pCacheId);
  }

  @Override
  public String getLastModified(final String pCacheId) throws Exception {
    return mLastModified.get(pCacheId);
  }

  @Override
  public void invalidate(String pCacheId) {
    mCache.remove(pCacheId);
    mLastModified.remove(pCacheId);
  }

  @Override
  public void flushAll() throws Exception {
    mCache.clear();
    mCacheList.clear();
    mCachedKeyList.clear();
    mLastModified.clear();
  }

  @Override
  public void put(String pCacheId, List<I> pReadOnlyIds) throws Exception {
    mCacheList.put(pCacheId, pReadOnlyIds);
  }

  @Override
  public void putKeys(String pCacheId, List<String> pKeys) throws Exception {
    mCachedKeyList.put(pCacheId, pKeys);
  }

  @Override
  public List<I> getList(String pCacheId) throws Exception {
    return mCacheList.get(pCacheId);
  }

  @Override
  public List<String> getCachedKeyList(String pCacheId) throws Exception {
    return mCachedKeyList.get(pCacheId);
  }

  @Override
  public void invalidateList(String pCacheId) throws Exception {
    mCacheList.remove(pCacheId);
  }
}

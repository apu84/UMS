package org.ums.cache;

import org.springframework.stereotype.Component;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocalCacheManager<R extends LastModifier, I> implements CacheManager<R, I> {
  private Map<String, R> mCache;
  private Map<String, List<I>> mCacheList;
  private Map<String, String> mLastModified;
  private Map<String, List<String>> mCachedKeyList;
  private Map<String, String> mReferredKeyMap;
  private Map<String, List<String>> mInverseReferredKeyMap;

  public LocalCacheManager() {
    mCache = new HashMap<>();
    mCacheList = new HashMap<>();
    mCachedKeyList = new HashMap<>();
    mLastModified = new HashMap<>();
    mReferredKeyMap = new HashMap<>();
    mInverseReferredKeyMap = new HashMap<>();
  }

  @Override
  public void put(String pCacheId, R pReadonly) {
    mCache.put(pCacheId, pReadonly);
    mLastModified.put(pCacheId, pReadonly.getLastModified());
  }

  @Override
  public R get(String pCacheId) {
    return mCache.get(pCacheId);
  }

  @Override
  public String getLastModified(final String pCacheId) {
    return mLastModified.get(pCacheId);
  }

  @Override
  public void invalidate(String pCacheId) {
    mCache.remove(pCacheId);
    mLastModified.remove(pCacheId);
    List<String> referrer = mInverseReferredKeyMap.get(pCacheId);
    if(referrer != null) {
      for(String key : referrer) {
        mReferredKeyMap.remove(key);
      }
    }

    mInverseReferredKeyMap.remove(pCacheId);
  }

  @Override
  public void flushAll() {
    mCache.clear();
    mCacheList.clear();
    mCachedKeyList.clear();
    mLastModified.clear();
    mReferredKeyMap.clear();
    mInverseReferredKeyMap.clear();
  }

  @Override
  public void put(String pCacheId, List<I> pReadOnlyIds) {
    mCacheList.put(pCacheId, pReadOnlyIds);
  }

  @Override
  public void putKeys(String pCacheId, List<String> pKeys) {
    mCachedKeyList.put(pCacheId, pKeys);
  }

  @Override
  public List<I> getList(String pCacheId) {
    return mCacheList.get(pCacheId);
  }

  @Override
  public List<String> getCachedKeyList(String pCacheId) {
    return mCachedKeyList.get(pCacheId);
  }

  @Override
  public void invalidateList(String pCacheId) {
    mCacheList.remove(pCacheId);
  }

  @Override
  public void putReferrerKey(String pReferrer, String pReferred) {
    mReferredKeyMap.put(pReferrer, pReferred);
    List<String> keyList = mInverseReferredKeyMap.get(pReferred);

    if(keyList == null) {
      keyList = new ArrayList<>();
      keyList.add(pReferrer);
    }
    else {
      if(!keyList.contains(pReferrer)) {
        keyList.add(pReferrer);
      }
    }

    mInverseReferredKeyMap.put(pReferred, keyList);
  }

  @Override
  public String getReferred(String pReferrer) {
    return mReferredKeyMap.get(pReferrer);
  }

  @Override
  public List<String> getReferrer(String pReferred) {
    return mInverseReferredKeyMap.get(pReferred);
  }
}

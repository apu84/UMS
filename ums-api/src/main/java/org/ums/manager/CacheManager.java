package org.ums.manager;

import java.util.List;

public interface CacheManager<R, I> {
  void put(String pCacheId, R pReadonly);

  void put(String pCacheId, List<I> pReadOnlyIds);

  void putObject(String pCacheId, Object object);

  Object getObject(String pCacheId);

  void putKeys(String pCacheId, List<String> pKeys);

  R get(String pCacheId);

  List<I> getList(String pCacheId);

  List<String> getCachedKeyList(String pCacheId);

  String getLastModified(String pCacheId);

  void invalidate(String pCacheId);

  void invalidateList(String pCacheId);

  void flushAll();

  void putReferrerKey(String pReferrer, String pReferred);

  String getReferred(String pReferrer);

  List<String> getReferrer(String pReferred);
}

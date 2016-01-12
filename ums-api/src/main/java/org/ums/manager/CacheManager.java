package org.ums.manager;


public interface CacheManager<R> {
  void put(String pCacheId, R pReadonly) throws Exception;

  R get(String pCacheId) throws Exception;

  String getLastModified(String pCacheId) throws Exception;

  void invalidate(String pCacheId) throws Exception;
}

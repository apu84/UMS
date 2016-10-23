package org.ums.manager;

import java.util.List;

public interface CacheManager<R, I> {
  void put(String pCacheId, R pReadonly) throws Exception;

  void put(String pCacheId, List<I> pReadOnlyIds) throws Exception;

  void putKeys(String pCacheId, List<String> pKeys) throws Exception;

  R get(String pCacheId) throws Exception;

  List<I> getList(String pCacheId) throws Exception;

  List<String> getCachedKeyList(String pCacheId) throws Exception;

  String getLastModified(String pCacheId) throws Exception;

  void invalidate(String pCacheId) throws Exception;

  void invalidateList(String pCacheId) throws Exception;

  void flushAll() throws Exception;
}

package org.ums.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class LocalCache {
  private Map<String, Object> mLocalCopy;

  public LocalCache() {
    mLocalCopy = new HashMap<String, Object>();
  }

  public Object cache(Callable<Object> func, Object pCacheId, Class pObjectClass) {

    String cacheKey = getCacheKey(pCacheId, pObjectClass);

    if(mLocalCopy.get(cacheKey) == null) {
      try {
        mLocalCopy.put(cacheKey, func.call());
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    }

    return mLocalCopy.get(cacheKey);
  }

  protected String getCacheKey(Object pCacheId, Class pClass) {
    return pClass.getName() + "-" + pCacheId;
  }

  public void invalidate() {
    mLocalCopy = null;
  }
}

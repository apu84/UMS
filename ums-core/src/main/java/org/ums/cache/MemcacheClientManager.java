package org.ums.cache;


import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;

import java.net.InetSocketAddress;

public class MemcacheClientManager<R extends LastModifier> implements CacheManager<R> {
  private static final Logger mLogger = LoggerFactory.getLogger(MemcacheClientManager.class);

  private MemcachedClient mObjectCache;
  private MemcachedClient mLastModified;

  private String mObjectCacheUrl;
  private Integer mObjectCachePort;
  private String mLastModifiedCacheUrl;
  private Integer mLastModifiedCachePort;

  public MemcacheClientManager() throws Exception {
    Validate.notNull(mObjectCacheUrl);
    Validate.notNull(mObjectCachePort);
    Validate.notNull(mLastModifiedCacheUrl);
    Validate.notNull(mLastModifiedCachePort);

    mObjectCache = new MemcachedClient(new InetSocketAddress(mObjectCacheUrl, mObjectCachePort));
    mLastModified = new MemcachedClient(new InetSocketAddress(mLastModifiedCacheUrl, mLastModifiedCachePort));

  }

  @Override
  public void put(String pCacheId, R pReadonly) {
    mObjectCache.set(pCacheId, 0, pReadonly);
    mLastModified.set(pCacheId, 0, pReadonly.getLastModified());
  }

  @Override
  public R get(String pCacheId) throws Exception {
    return (R) mObjectCache.get(pCacheId);
  }

  @Override
  public String getLastModified(final String pCacheId) throws Exception {
    return (String) mLastModified.get(pCacheId);
  }

  @Override
  public void invalidate(String pCacheId) {
    mObjectCache.delete(pCacheId);
    mLastModified.delete(pCacheId);
  }

  @Override
  public void flushAll() throws Exception {
    mObjectCache.flush();
    mLastModified.flush();
  }

  public void setObjectCacheUrl(String pObjectCacheUrl) {
    mObjectCacheUrl = pObjectCacheUrl;
  }

  public void setObjectCachePort(Integer pObjectCachePort) {
    mObjectCachePort = pObjectCachePort;
  }

  public void setLastModifiedCacheUrl(String pLastModifiedCacheUrl) {
    mLastModifiedCacheUrl = pLastModifiedCacheUrl;
  }

  public void setLastModifiedCachePort(Integer pLastModifiedCachePort) {
    mLastModifiedCachePort = pLastModifiedCachePort;
  }
}

package org.ums.cache;

import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;

import java.net.InetSocketAddress;
import java.util.List;

public class MemcacheClientManager<R extends LastModifier, I> implements CacheManager<R, I> {
  private static final Logger mLogger = LoggerFactory.getLogger(MemcacheClientManager.class);

  private MemcachedClient mObjectCache;
  private MemcachedClient mLastModified;

  public MemcacheClientManager(final String pObjectCacheUrl, final Integer pObjectCachePort,
      final String pLastModifiedCacheUrl, final Integer pLastModifiedCachePort) throws Exception {
    Validate.notNull(pObjectCacheUrl);
    Validate.notNull(pObjectCachePort);
    Validate.notNull(pLastModifiedCacheUrl);
    Validate.notNull(pLastModifiedCachePort);
    mLogger.info("Memcached - " + pObjectCacheUrl + " : " + pObjectCachePort);
    mLogger.info("Memcached - " + pLastModifiedCacheUrl + " : " + pLastModifiedCachePort);
    mObjectCache = new MemcachedClient(new InetSocketAddress(pObjectCacheUrl, pObjectCachePort));
    mLastModified =
        new MemcachedClient(new InetSocketAddress(pLastModifiedCacheUrl, pLastModifiedCachePort));
  }

  @Override
  public void put(String pCacheId, R pReadonly) {
    mObjectCache.set(pCacheId, 0, pReadonly);
    mLastModified.set(pCacheId, 0, StringUtils.isEmpty(pReadonly.getLastModified()) ? ""
        : pReadonly.getLastModified());
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

  @Override
  public void put(String pCacheId, List<I> pReadOnlyIds) throws Exception {
    mObjectCache.set(pCacheId, 0, pReadOnlyIds);
  }

  @Override
  public void putKeys(String pCacheId, List<String> pKeys) throws Exception {
    mObjectCache.set(pCacheId, 0, pKeys);
  }

  @Override
  public List<I> getList(String pCacheId) throws Exception {
    return (List<I>) mObjectCache.get(pCacheId);
  }

  @Override
  public List<String> getCachedKeyList(String pCacheId) throws Exception {
    return (List<String>) mObjectCache.get(pCacheId);
  }

  @Override
  public void invalidateList(String pCacheId) throws Exception {
    mObjectCache.delete(pCacheId);
  }
}

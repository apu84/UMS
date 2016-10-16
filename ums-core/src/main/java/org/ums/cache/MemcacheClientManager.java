package org.ums.cache;


import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MemcacheClientManager<R extends LastModifier, I> implements CacheManager<R, I> {
  private static final Logger mLogger = LoggerFactory.getLogger(MemcacheClientManager.class);

  private MemcachedClient mObjectCache;


  public MemcacheClientManager(final String pObjectCacheUrl, final Integer pObjectCachePort) throws Exception {
    Validate.notNull(pObjectCacheUrl);
    Validate.notNull(pObjectCachePort);

    mObjectCache = new MemcachedClient(new InetSocketAddress(pObjectCacheUrl, pObjectCachePort));
  }

  @Override
  public void put(String pCacheId, R pReadonly) {
    mObjectCache.set(pCacheId, 0, pReadonly);
    mObjectCache.set(getLastModifiedKey(pCacheId), 0, StringUtils.isEmpty(pReadonly.getLastModified()) ? "": pReadonly.getLastModified());
  }

  @Override
  public R get(String pCacheId) throws Exception {
    return (R) mObjectCache.get(pCacheId);
  }

  @Override
  public String getLastModified(final String pCacheId) throws Exception {
    return (String) mObjectCache.get(getLastModifiedKey(pCacheId));
  }

  @Override
  public void invalidate(String pCacheId) {
    mObjectCache.delete(pCacheId);
    mObjectCache.delete(getLastModifiedKey(pCacheId));
  }

  @Override
  public void flushAll() throws Exception {
    mObjectCache.flush();
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

  @Override
  public void putReferrerKey(String pReferrer, String pReferred) {
    mObjectCache.set(pReferrer, 0, pReferred);

    List<String> keyList = (List<String>) mObjectCache.get(getReferenceKey(pReferred));

    if (keyList == null) {
      keyList = new ArrayList<>();
      keyList.add(pReferrer);
    } else {
      if (!keyList.contains(pReferrer)) {
        keyList.add(pReferrer);
      }
    }

    mObjectCache.set(getReferenceKey(pReferred), 0, keyList);
  }

  @Override
  public String getReferred(String pReferrer) {
    return (String) mObjectCache.get(pReferrer);
  }

  @Override
  public List<String> getReferrer(String pReferred) {
    return (List<String>) mObjectCache.get(pReferred);
  }

  private String getReferenceKey(String pId) {
    return pId + "-reference";
  }

  private String getLastModifiedKey(String pId) {
    return pId + "-lastModified";
  }
}

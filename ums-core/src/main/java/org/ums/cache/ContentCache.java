package org.ums.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;
import org.ums.manager.ContentManager;
import org.ums.util.CacheUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class ContentCache<R extends Identifier<I> & LastModifier, M extends R, I, C extends ContentManager<R, M, I>>
    extends ContentDaoDecorator<R, M, I, C> {
  private static final Logger mLogger = LoggerFactory.getLogger(TeacherCache.class);

  @Override
  public List<R> getAll() {
    List<R> readOnlys = super.getAll();
    for (R readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public I create(M pMutable) {
    I created = super.create(pMutable);
    getCacheManager().invalidateList(getCachedListKey());
    return created;
  }

  @Override
  public List<I> create(List<M> pMutableList) {
    List<I> created = super.create(pMutableList);
    getCacheManager().invalidateList(getCachedListKey());
    return created;
  }

  @Override
  public int delete(M pMutable) {
    int modified = super.delete(pMutable);
    invalidate(pMutable);
    getCacheManager().invalidateList(getCachedListKey());
    return modified;
  }

  @Override
  public int delete(List<M> pMutableList) {
    int modified = super.delete(pMutableList);
    for (M mutable : pMutableList) {
      invalidate(mutable);
    }
    getCacheManager().invalidateList(getCachedListKey());
    return modified;
  }

  @Override
  public int update(M pMutable) {
    int modified = super.update(pMutable);
    invalidate(pMutable);
    getCacheManager().invalidateList(getCachedListKey());
    return modified;
  }

  @Override
  public int update(List<M> pMutableList) {
    int modified = super.update(pMutableList);
    for (M mutable : pMutableList) {
      invalidate(mutable);
    }
    getCacheManager().invalidateList(getCachedListKey());
    return modified;
  }

  @Override
  public R get(I pId) {
    String cacheKey = getCacheKey(pId);
    R pReadonly = getCacheManager().get(cacheKey);
    if (pReadonly == null) {
      pReadonly = super.get(pId);
      getCacheManager().put(cacheKey, pReadonly);
    }
    return pReadonly;
  }

  @Override
  public void invalidateCache(M pMutable) {
    invalidate(pMutable);
    getCacheManager().invalidateList(getCachedListKey());
  }

  @Override
  public R validate(R pReadonly) {
    String cacheKey = getCacheKey(pReadonly.getId());
    String lastModified = getCacheManager().getLastModified(cacheKey);

    if (StringUtils.isEmpty(lastModified)
        || StringUtils.isEmpty(pReadonly.getLastModified())
        || (!StringUtils.isEmpty(lastModified) && !StringUtils.isEmpty(pReadonly.getLastModified()) && lastModified
        .compareTo(pReadonly.getLastModified()) > 0)) {
      R readOnly = super.get(pReadonly.getId());
      getCacheManager().invalidate(cacheKey);
      getCacheManager().put(getCacheKey(pReadonly.getId()), readOnly);
      pReadonly = readOnly;
    }
    return pReadonly;

  }

  @Override
  public boolean exists(I pId) {
    String cacheKey = getCacheKey(pId);
    R pReadonly = getCacheManager().get(cacheKey);
    return pReadonly != null || super.exists(pId);
  }

  protected void invalidate(final R pReadonly) {
    getCacheManager().invalidate(getCacheKey(pReadonly.getId()));
  }

  protected abstract CacheManager<R, I> getCacheManager();

  protected String getCacheKey(I pId) {
    return CacheUtil.getCacheKey(getClassOfR(), pId);
  }

  @SuppressWarnings("unchecked")
  private Class<R> getClassOfR() {
    ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<R>) superclass.getActualTypeArguments()[0];
  }

  protected String getCacheKey(final String pEntityName, final Object... args) {
    StringBuilder cacheKeyBuilder = new StringBuilder();
    cacheKeyBuilder.append(pEntityName).append("-");
    for (Object param : args) {
      cacheKeyBuilder.append(param).append("-");
    }
    return cacheKeyBuilder.toString().replace(" ", "");
  }

  protected String getCachedListKey() {
    return "all_cached_list";
  }

  protected List<R> cachedList(final String pCacheKey, Callable<List<R>> pCallable) {
    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    String cacheKey = pCacheKey + methodName;

    List<I> cachedList = getCacheManager().getList(cacheKey);

    if (cachedList == null || cachedList.size() == 0) {
      // if list is not present in cache
      List<R> entityList = null;
      try {
        entityList = pCallable.call();
      } catch (Exception e) {
        mLogger.error("Exception while caching entity list", e);
        throw new RuntimeException(e);
      }
      if (entityList.size() > 0) {
        List<I> entityCacheIds = new ArrayList<>();

        for (R entity : entityList) {
          String entityCacheKey = getCacheKey(entity.getId());
          getCacheManager().put(entityCacheKey, entity);
          entityCacheIds.add(entity.getId());
        }
        getCacheManager().put(cacheKey, entityCacheIds);

        // update list cache
        List<String> cachedLists = getCacheManager().getCachedKeyList(getCachedListKey());
        if (cachedLists == null) {
          cachedLists = new ArrayList<>();
        }
        if (!cachedLists.contains(cacheKey)) {
          cachedLists.add(cacheKey);
        }
        getCacheManager().putKeys(getCachedListKey(), cachedLists);
      }
      return entityList;

    } else {
      // list is present in cache
      List<R> entities = new ArrayList<>();
      long currentTime = System.currentTimeMillis();
      for (I entityId : cachedList) {
        R entity = get(entityId);
        entities.add(entity);
      }
      long afterTime = System.currentTimeMillis();
      long diff = afterTime - currentTime;
      if (diff > 100)
        mLogger.debug("Time taken to build cached list: {} ms", diff);
      return entities;

    }
  }

  protected R cachedEntity(final String pCacheKey, Callable<R> pCallable) {
    String referredKey = getCacheManager().getReferred(pCacheKey);
    if (StringUtils.isEmpty(referredKey)) {
      return cache(pCacheKey, pCallable);
    } else {
      R cachedEntity = getCacheManager().get(referredKey);
      if (cachedEntity != null) {
        return cachedEntity;
      }
      return cache(pCacheKey, pCallable);
    }
  }

  private R cache(final String pCacheKey, Callable<R> pCallable) {
    R readonly = null;
    try {
      readonly = pCallable.call();
    } catch (Exception e) {
      mLogger.error("Exception while caching entity", e);
      throw new RuntimeException(e);
    }
    if (readonly != null) {
      String idCacheKey = getCacheKey(readonly.getId());
      getCacheManager().put(idCacheKey, readonly);
      getCacheManager().putReferrerKey(pCacheKey, idCacheKey);
    }
    return readonly;
  }
}

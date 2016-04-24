package org.ums.cache;

import org.springframework.util.StringUtils;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.manager.CacheManager;
import org.ums.manager.ContentManager;

import java.util.List;

public abstract class ContentCache<R extends Identifier<I> & LastModifier, M extends R, I, C extends ContentManager<R, M, I>> extends ContentDaoDecorator<R, M, I, C> {

  @Override
  public List<R> getAll() throws Exception {
    List<R> readOnlys = super.getAll();
    for (R readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public int delete(M pMutable) throws Exception {
    int modified = super.delete(pMutable);
    invalidate(pMutable);
    return modified;
  }

  @Override
  public int delete(List<M> pMutableList) throws Exception {
    int modified = super.delete(pMutableList);
    for (M mutable : pMutableList) {
      invalidate(mutable);
    }
    return modified;
  }

  @Override
  public int update(M pMutable) throws Exception {
    int modified = super.update(pMutable);
    invalidate(pMutable);
    return modified;
  }

  @Override
  public int update(List<M> pMutableList) throws Exception {
    int modified = super.update(pMutableList);
    for (M mutable : pMutableList) {
      invalidate(mutable);
    }
    return modified;
  }

  @Override
  public R get(I pId) throws Exception {
    String cacheKey = getCacheKey(pId);
    R pReadonly = getCacheManager().get(cacheKey);
    if (pReadonly == null) {
      pReadonly = super.get(pId);
      getCacheManager().put(cacheKey, pReadonly);
    }
    return pReadonly;
  }

  @Override
  public R validate(R pReadonly) throws Exception {
    String cacheKey = getCacheKey(pReadonly.getId());
    String lastModified = getCacheManager().getLastModified(cacheKey);

    if (StringUtils.isEmpty(lastModified)
        || StringUtils.isEmpty(pReadonly.getLastModified())
        || (!StringUtils.isEmpty(lastModified) && !StringUtils.isEmpty(pReadonly.getLastModified())
        && lastModified.compareTo(pReadonly.getLastModified()) > 0)) {
      R readOnly = super.get(pReadonly.getId());
      getCacheManager().invalidate(cacheKey);
      getCacheManager().put(getCacheKey(pReadonly.getId()), readOnly);
      pReadonly = readOnly;
    }
    return pReadonly;

  }

  @Override
  public boolean exists(I pId) throws Exception {
    String cacheKey = getCacheKey(pId);
    R pReadonly = getCacheManager().get(cacheKey);
    return pReadonly != null || super.exists(pId);
  }

  protected void invalidate(final R pReadonly) throws Exception {
    getCacheManager().invalidate(getCacheKey(pReadonly.getId()));
  }

  protected abstract CacheManager<R> getCacheManager();

  protected abstract String getCacheKey(I pId);
}

package org.ums.academic.dao;

import org.ums.domain.model.common.Identifier;
import org.ums.manager.CacheManager;

import java.util.List;

public abstract class ContentCache<R extends Identifier<I>, M extends R, I> extends ContentDaoDecorator<R, M, I> {

  @Override
  public List<R> getAll() throws Exception {
    List<R> readOnlys = super.getAll();
    for (R readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public void delete(M pMutable) throws Exception {
    super.delete(pMutable);
    invalidate(pMutable);
  }

  @Override
  public void update(M pMutable) throws Exception {
    super.update(pMutable);
    invalidate(pMutable);
  }

  @Override
  public R get(I pId) throws Exception {
    String cacheKey = getCacheKey(pId);
    R pReadonly = (R) getCacheManager().get(cacheKey);
    if (pReadonly == null) {
      pReadonly = super.get(pId);
      getCacheManager().put(cacheKey, pReadonly);
    }
    return pReadonly;
  }

  protected void invalidate(final R pReadonly) throws Exception {
    getCacheManager().invalidate(getCacheKey(pReadonly.getId()));
  }

  protected abstract CacheManager<R> getCacheManager();

  protected abstract String getCacheKey(I pId);
}

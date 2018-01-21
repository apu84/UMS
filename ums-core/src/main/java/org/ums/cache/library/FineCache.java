package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.CacheManager;
import org.ums.manager.library.FineManager;

import java.util.List;

public class FineCache extends ContentCache<Fine, MutableFine, Long, FineManager> implements FineManager {

  CacheManager<Fine, Long> mCacheManager;

  public FineCache(CacheManager<Fine, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Fine, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Fine> getFines(String pPatronId) {
    return getManager().getFines(pPatronId);
  }

  @Override
  public int saveFine(MutableFine pMutableFine) {
    return getManager().saveFine(pMutableFine);
  }

  @Override
  public int updateFine(MutableFine pMutableFine) {
    return getManager().updateFine(pMutableFine);
  }

  @Override
  public Fine getFine(Long pCirculationId) {
    return getManager().getFine(pCirculationId);
  }
}

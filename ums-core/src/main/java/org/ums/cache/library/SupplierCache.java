package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.CacheManager;
import org.ums.manager.library.SupplierManager;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class SupplierCache extends
    ContentCache<Supplier, MutableSupplier, Integer, SupplierManager> implements SupplierManager {

  CacheManager<Supplier, Integer> mCacheManager;

  public SupplierCache(CacheManager<Supplier, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Supplier, Integer> getCacheManager() {
    return mCacheManager;
  }
}

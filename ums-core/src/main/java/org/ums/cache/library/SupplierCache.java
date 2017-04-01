package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.CacheManager;
import org.ums.manager.library.SupplierManager;

import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class SupplierCache extends ContentCache<Supplier, MutableSupplier, Long, SupplierManager> implements
    SupplierManager {

  CacheManager<Supplier, Long> mCacheManager;

  public SupplierCache(CacheManager<Supplier, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<Supplier> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pWhereClause,
      final String pOrder) {
    return getManager().getAllForPagination(pItemPerPage, pPage, pWhereClause, pOrder);
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    return getManager().getTotalForPagination(pWhereClause);
  }

  @Override
  protected CacheManager<Supplier, Long> getCacheManager() {
    return mCacheManager;
  }
}

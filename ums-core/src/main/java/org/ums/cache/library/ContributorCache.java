package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.CacheManager;
import org.ums.manager.library.ContributorManager;
import org.ums.manager.library.SupplierManager;

import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class ContributorCache extends ContentCache<Contributor, MutableContributor, Long, ContributorManager> implements
    ContributorManager {

  CacheManager<Contributor, Long> mCacheManager;

  public ContributorCache(CacheManager<Contributor, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<Contributor> getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pWhereClause, final String pOrder) {
    return getManager().getAllForPagination(pItemPerPage, pPage, pWhereClause, pOrder);
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    return getManager().getTotalForPagination(pWhereClause);
  }

  @Override
  protected CacheManager<Contributor, Long> getCacheManager() {
    return mCacheManager;
  }
}

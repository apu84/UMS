package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.CacheManager;
import org.ums.manager.library.AuthorManager;
import org.ums.manager.library.PublisherManager;

import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class PublisherCache extends ContentCache<Publisher, MutablePublisher, Integer, PublisherManager> implements
    PublisherManager {

  CacheManager<Publisher, Integer> mCacheManager;

  public PublisherCache(CacheManager<Publisher, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<Publisher> getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pWhereClause, final String pOrder) {
    return getManager().getAllForPagination(pItemPerPage, pPage, pWhereClause, pOrder);
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    return getManager().getTotalForPagination(pWhereClause);
  }

  @Override
  protected CacheManager<Publisher, Integer> getCacheManager() {
    return mCacheManager;
  }
}

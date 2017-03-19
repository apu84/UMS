package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.CacheManager;
import org.ums.manager.library.AuthorManager;
import org.ums.manager.library.PublisherManager;

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
  protected CacheManager<Publisher, Integer> getCacheManager() {
    return mCacheManager;
  }
}

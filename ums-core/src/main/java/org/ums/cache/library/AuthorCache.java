package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.CacheManager;
import org.ums.manager.library.AuthorManager;

/**
 * Created by Ifti on 30-Jan-17.
 */
public class AuthorCache extends ContentCache<Author, MutableAuthor, Integer, AuthorManager>
    implements AuthorManager {

  CacheManager<Author, Integer> mCacheManager;

  public AuthorCache(CacheManager<Author, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Author, Integer> getCacheManager() {
    return mCacheManager;
  }
}

package org.ums.cache;

import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.manager.CacheManager;
import org.ums.manager.LibraryManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by kawsu on 12/3/2016.
 */
public class LibraryCache extends ContentCache<Library, MutableLibrary, Integer, LibraryManager>
    implements LibraryManager {
  private CacheManager<Library, Integer> mCacheManager;

  public LibraryCache(final CacheManager<Library, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Library, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Library.class, pId);
  }

  @Override
  public List<Library> getLibraryBooks(final String pbook) throws Exception {
    return getManager().getLibraryBooks(pbook);
  }

}

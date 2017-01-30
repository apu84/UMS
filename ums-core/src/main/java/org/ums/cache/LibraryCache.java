package org.ums.cache;

import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.manager.CacheManager;
import org.ums.manager.LibraryManager;
import org.ums.util.CacheUtil;

import java.util.List;

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
  public List<Library> getLibraryBooks(final String pbook) throws Exception {
    return getManager().getLibraryBooks(pbook);
  }

  @Override
  public List<Library> getAllTheLibraryBooks() throws Exception {
    return getManager().getAllTheLibraryBooks();
  }

  @Override
  public int deleteByBookNameAndAuthorName(String pBookName, String pAuthorName) {
    return getManager().deleteByBookNameAndAuthorName(pBookName, pAuthorName);
  }

}

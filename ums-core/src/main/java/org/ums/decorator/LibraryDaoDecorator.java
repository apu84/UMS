package org.ums.decorator;

import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.manager.LibraryManager;

import java.util.List;

/**
 * Created by kawsu on 12/3/2016.
 */
public class LibraryDaoDecorator extends ContentDaoDecorator<Library, MutableLibrary, Integer, LibraryManager>
    implements LibraryManager {

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

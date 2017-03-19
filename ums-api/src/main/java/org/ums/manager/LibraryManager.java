package org.ums.manager;

import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import java.util.List;

/**
 * Created by kawsu on 12/3/2016.
 */
public interface LibraryManager extends ContentManager<Library, MutableLibrary, Integer> {

  List<Library> getLibraryBooks(final String pbook) throws Exception;

  List<Library> getAllTheLibraryBooks() throws Exception;

  int deleteByBookNameAndAuthorName(final String pBookName, final String pAuthorName);
}

package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 30-Jan-17.
 */
public interface AuthorManager extends ContentManager<Author, MutableAuthor, Integer> {

}

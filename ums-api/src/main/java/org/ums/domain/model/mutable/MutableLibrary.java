package org.ums.domain.model.mutable;

/**
 * Created by kawsu on 12/3/2016.
 */

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Library;

public interface MutableLibrary extends Mutable, MutableIdentifier<Integer>, MutableLastModifier,
    Library {

  void setBookName(final String bookName);

  void setAuthorName(final String authorName);
}

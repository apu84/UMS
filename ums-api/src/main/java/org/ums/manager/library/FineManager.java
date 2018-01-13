package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.ContentManager;

import java.util.List;

public interface FineManager extends ContentManager<Fine, MutableFine, Long> {
  List<Fine> getFines(final String pPatronId);

  int saveFine(final MutableFine pMutableFine);

  int updateFine(final MutableFine pMutableFine);

}

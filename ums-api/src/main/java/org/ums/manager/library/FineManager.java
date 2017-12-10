package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.ContentManager;

public interface FineManager extends ContentManager<Fine, MutableFine, Long> {
}

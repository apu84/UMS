package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.ContentManager;

public interface CirculationManager extends ContentManager<Circulation, MutableCirculation, Long> {
}

package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.library.CirculationManager;

public class CirculationDaoDecorator extends
    ContentDaoDecorator<Circulation, MutableCirculation, Long, CirculationManager> implements CirculationManager {
}

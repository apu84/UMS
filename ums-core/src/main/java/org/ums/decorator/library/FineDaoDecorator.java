package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.library.FineManager;

public class FineDaoDecorator extends ContentDaoDecorator<Fine, MutableFine, Long, FineManager> implements FineManager {
}

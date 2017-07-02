package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Thana;
import org.ums.domain.model.mutable.common.MutableThana;
import org.ums.manager.common.ThanaManager;

public class ThanaDaoDecorator extends ContentDaoDecorator<Thana, MutableThana, Integer, ThanaManager> implements
    ThanaManager {
}

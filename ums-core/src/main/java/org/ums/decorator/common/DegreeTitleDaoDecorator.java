package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.manager.common.DegreeTitleManager;

public class DegreeTitleDaoDecorator extends
    ContentDaoDecorator<DegreeTitle, MutableDegreeTitle, Integer, DegreeTitleManager> implements DegreeTitleManager {
}

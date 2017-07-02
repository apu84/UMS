package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.common.MutableDivision;
import org.ums.manager.common.DivisionManager;

public class DivisionDaoDecorator extends ContentDaoDecorator<Division, MutableDivision, Integer, DivisionManager>
    implements DivisionManager {
}

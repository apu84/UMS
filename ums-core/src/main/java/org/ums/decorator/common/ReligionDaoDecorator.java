package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.common.MutableReligion;
import org.ums.manager.common.ReligionManager;

public class ReligionDaoDecorator extends ContentDaoDecorator<Religion, MutableReligion, Integer, ReligionManager>
    implements ReligionManager {
}

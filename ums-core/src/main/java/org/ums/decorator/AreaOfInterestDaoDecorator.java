package org.ums.decorator;

import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.mutable.MutableAreaOfInterest;
import org.ums.manager.AreaOfInterestManager;

public class AreaOfInterestDaoDecorator extends
    ContentDaoDecorator<AreaOfInterest, MutableAreaOfInterest, Integer, AreaOfInterestManager> implements
    AreaOfInterestManager {
}

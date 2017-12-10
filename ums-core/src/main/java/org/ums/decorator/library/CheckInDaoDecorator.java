package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.domain.model.mutable.library.MutableCheckIn;
import org.ums.manager.library.CheckInManager;

public class CheckInDaoDecorator extends ContentDaoDecorator<CheckIn, MutableCheckIn, Long, CheckInManager> implements
    CheckInManager {
}

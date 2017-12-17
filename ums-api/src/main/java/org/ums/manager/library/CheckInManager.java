package org.ums.manager.library;

import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.domain.model.mutable.library.MutableCheckIn;
import org.ums.manager.ContentManager;

public interface CheckInManager extends ContentManager<CheckIn, MutableCheckIn, Long> {
}

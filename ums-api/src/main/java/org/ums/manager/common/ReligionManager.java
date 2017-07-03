package org.ums.manager.common;

import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.common.MutableReligion;
import org.ums.manager.ContentManager;

public interface ReligionManager extends ContentManager<Religion, MutableReligion, Integer> {
}

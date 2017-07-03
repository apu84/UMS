package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.domain.model.mutable.common.MutableBloodGroup;
import org.ums.manager.common.BloodGroupManager;

public class BloodGroupDaoDecorator extends
    ContentDaoDecorator<BloodGroup, MutableBloodGroup, Integer, BloodGroupManager> implements BloodGroupManager {
}

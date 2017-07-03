package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;
import org.ums.manager.common.MaritalStatusManager;

public class MaritalStatusDaoDecorator extends
    ContentDaoDecorator<MaritalStatus, MutableMaritalStatus, Integer, MaritalStatusManager> implements
    MaritalStatusManager {
}

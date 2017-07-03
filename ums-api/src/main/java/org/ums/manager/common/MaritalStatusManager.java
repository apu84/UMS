package org.ums.manager.common;

import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;
import org.ums.manager.ContentManager;

public interface MaritalStatusManager extends ContentManager<MaritalStatus, MutableMaritalStatus, Integer> {
}

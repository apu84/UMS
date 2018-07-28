package org.ums.manager.common;

import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.manager.ContentManager;

public interface EmployeeEarnedLeaveBalanceHistoryManager extends
    ContentManager<EmployeeEarnedLeaveBalanceHistory, MutableEmployeeEarnedLeaveBalanceHistory, Long> {
}

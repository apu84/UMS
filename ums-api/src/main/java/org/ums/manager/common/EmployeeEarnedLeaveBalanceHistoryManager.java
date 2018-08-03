package org.ums.manager.common;

import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.manager.ContentManager;

import java.util.List;

public interface EmployeeEarnedLeaveBalanceHistoryManager extends
    ContentManager<EmployeeEarnedLeaveBalanceHistory, MutableEmployeeEarnedLeaveBalanceHistory, Long> {

  List<EmployeeEarnedLeaveBalanceHistory> getAllEarnedLeaveBalanceHistoryOfActiveEmployees();
}

package org.ums.manager.common;

import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public interface EmployeeEarnedLeaveBalanceManager extends
    ContentManager<EmployeeEarnedLeaveBalance, MutableEmployeeEarnedLeaveBalance, Long> {
  EmployeeEarnedLeaveBalance getEarnedLeaveBalance(String pEmployeeId);

  List<EmployeeEarnedLeaveBalance> getAllEarnedLeaveBalanceOfActiveEmployees();
}

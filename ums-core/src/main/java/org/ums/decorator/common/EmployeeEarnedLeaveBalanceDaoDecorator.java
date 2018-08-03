package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public class EmployeeEarnedLeaveBalanceDaoDecorator
    extends
    ContentDaoDecorator<EmployeeEarnedLeaveBalance, MutableEmployeeEarnedLeaveBalance, Long, EmployeeEarnedLeaveBalanceManager>
    implements EmployeeEarnedLeaveBalanceManager {
  @Override
  public EmployeeEarnedLeaveBalance getEarnedLeaveBalance(String pEmployeeId) {
    return getManager().getEarnedLeaveBalance(pEmployeeId);
  }

  @Override
  public List<EmployeeEarnedLeaveBalance> getAllEarnedLeaveBalanceOfActiveEmployees() {
    return getManager().getAllEarnedLeaveBalanceOfActiveEmployees();
  }
}

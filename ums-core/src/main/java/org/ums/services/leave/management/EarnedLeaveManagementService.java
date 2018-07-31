package org.ums.services.leave.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.enums.common.LeaveBalanceType;
import org.ums.enums.common.LeaveCategories;
import org.ums.enums.common.LeaveMigrationType;
import org.ums.enums.common.LeaveTypeCategory;
import org.ums.generator.IdGenerator;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceHistoryManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceManager;
import org.ums.persistent.model.common.PersistentEmployeeEarnedLeaveBalance;
import org.ums.persistent.model.common.PersistentEmployeeEarnedLeaveBalanceHistory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 31-Jul-18.
 */
@Service
public class EarnedLeaveManagementService {

  @Autowired
  private EmployeeEarnedLeaveBalanceManager mEmployeeEarnedLeaveBalanceManager;
  @Autowired
  private EmployeeEarnedLeaveBalanceHistoryManager mEmployeeEarnedLeaveBalanceHistoryManager;
  @Autowired
  private IdGenerator mIdGenerator;

  @Transactional
  public void updatedEarnedLeave(LmsAppStatus pLmsAppStatus) {
    MutableEmployeeEarnedLeaveBalance employeeEarnedLeaveBalance = updatedEarnedLeaveBalance(pLmsAppStatus);
    MutableEmployeeEarnedLeaveBalanceHistory earnedLeaveBalanceHistory =
        updatedEarnedLeaveBalanceHistory(employeeEarnedLeaveBalance, pLmsAppStatus);

    mEmployeeEarnedLeaveBalanceManager.update(employeeEarnedLeaveBalance);
    mEmployeeEarnedLeaveBalanceHistoryManager.create(earnedLeaveBalanceHistory);
  }

  public MutableEmployeeEarnedLeaveBalance updatedEarnedLeaveBalance(LmsAppStatus pLmsAppStatus) {
    MutableEmployeeEarnedLeaveBalance earnedLeaveBalance =
        (MutableEmployeeEarnedLeaveBalance) mEmployeeEarnedLeaveBalanceManager.getEarnedLeaveBalance(pLmsAppStatus
            .getLmsApplication().getEmployeeId());
    earnedLeaveBalance.setEmployeeId(pLmsAppStatus.getLmsApplication().getEmployeeId());
    if(pLmsAppStatus.getLmsApplication().getLmsType().getId().equals(LeaveCategories.EARNED_LEAVE_ON_FULL_PAY.getId())) {
      earnedLeaveBalance.setFullPay(earnedLeaveBalance.getFullPay().subtract(
          new BigDecimal(pLmsAppStatus.getLmsApplication().getTotalDays())));
    }
    else if(pLmsAppStatus.getLmsApplication().getLmsType().getId()
        .equals(LeaveCategories.EARNED_LEAVE_ON_HALF_PAY.getId())) {
      earnedLeaveBalance.setHalfPay(earnedLeaveBalance.getHalfPay().subtract(
          new BigDecimal(pLmsAppStatus.getLmsApplication().getTotalDays())));
    }
    earnedLeaveBalance.setUpdatedOn(new Date());
    return earnedLeaveBalance;
  }

  public MutableEmployeeEarnedLeaveBalanceHistory updatedEarnedLeaveBalanceHistory(
      EmployeeEarnedLeaveBalance pEmployeeEarnedLeaveBalance, LmsAppStatus pLmsAppStatus) {
    MutableEmployeeEarnedLeaveBalanceHistory earnedLeaveBalanceHistory =
        new PersistentEmployeeEarnedLeaveBalanceHistory();
    earnedLeaveBalanceHistory.setId(mIdGenerator.getNumericId());
    earnedLeaveBalanceHistory.setEmployeeId(pEmployeeEarnedLeaveBalance.getEmployeeId());
    earnedLeaveBalanceHistory.setLeaveMigrationType(LeaveMigrationType.LEAVE_APPLICATION);
    earnedLeaveBalanceHistory.setChangedOn(new Date());
    earnedLeaveBalanceHistory.setDebit(new BigDecimal(pLmsAppStatus.getLmsApplication().getTotalDays()));
    earnedLeaveBalanceHistory.setCredit(new BigDecimal(0));
    if(pLmsAppStatus.getLmsApplication().getLmsType().getId().equals(LeaveCategories.EARNED_LEAVE_ON_FULL_PAY.getId())) {
      earnedLeaveBalanceHistory.setBalance(pEmployeeEarnedLeaveBalance.getFullPay());
      earnedLeaveBalanceHistory.setBalanceType(LeaveBalanceType.FULL_PAY);
    }
    else {
      earnedLeaveBalanceHistory.setBalance(pEmployeeEarnedLeaveBalance.getHalfPay());
      earnedLeaveBalanceHistory.setBalanceType(LeaveBalanceType.HALF_PAY);
    }
    return earnedLeaveBalanceHistory;
  }
}

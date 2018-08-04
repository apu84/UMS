package org.ums.domain.model.mutable.common;

import java.math.BigDecimal;
import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.LeaveBalanceType;
import org.ums.enums.common.LeaveMigrationType;

public interface MutableEmployeeEarnedLeaveBalanceHistory extends EmployeeEarnedLeaveBalanceHistory, Editable<Long>,
    MutableIdentifier<Long>, MutableLastModifier {

  void setEmployee(Employee pEmployee);

  void setEmployeeId(String pEmployeeId);

  void setLeaveMigrationType(LeaveMigrationType pLeaveMigrationType);

  void setChangedOn(Date pChangedOn);

  void setDebit(BigDecimal pDebit);

  void setCredit(BigDecimal pCredit);

  void setBalance(BigDecimal pBalance);

  void setBalanceType(LeaveBalanceType pBalanceType);
}

package org.ums.domain.model.immutable.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.enums.common.LeaveBalanceType;
import org.ums.enums.common.LeaveMigrationType;

public interface EmployeeEarnedLeaveBalanceHistory extends Serializable,
    EditType<MutableEmployeeEarnedLeaveBalanceHistory>, LastModifier, Identifier<Long> {

  Employee getEmployee();

  String getEmployeeId();

  LeaveMigrationType getLeaveMigrationType();

  Date getChangedOn();

  BigDecimal getDebit();

  BigDecimal getCredit();

  BigDecimal getBalance();

  LeaveBalanceType getBalanceType();
}

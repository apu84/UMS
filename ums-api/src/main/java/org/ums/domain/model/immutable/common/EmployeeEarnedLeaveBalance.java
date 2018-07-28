package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public interface EmployeeEarnedLeaveBalance extends Serializable, EditType<MutableEmployeeEarnedLeaveBalance>,
    LastModifier, Identifier<Long> {

  Employee getEmployee();

  String getEmployeeId();

  BigDecimal getFullPay();

  BigDecimal getHalfPay();

  Date getCreatedOn();

  Date getUpdatedOn();
}

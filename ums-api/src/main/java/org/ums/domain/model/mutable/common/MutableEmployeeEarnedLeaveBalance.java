package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public interface MutableEmployeeEarnedLeaveBalance
    extends
    EmployeeEarnedLeaveBalance,
    Editable<Long>,
    MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployee(Employee pEmployee);

  void setEmployeeId(String pEmployeeId);

  void setFullPay(BigDecimal pFullPay);

  void setHalfPay(BigDecimal pHalfPay);

  void setCreatedOn(Date pCreatedOn);

  void setUpdatedOn(Date pUpdatedOn);
}
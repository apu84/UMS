package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsApplication extends Serializable, LastModifier, EditType<MutableLmsApplication>, Identifier<Long> {
  String getEmployeeId();

  Employee getEmployee();

  int getLeaveTypeId();

  LmsType getLmsType();

  Date getAppliedOn();

  Date getFromDate();

  Date getToDate();

  String getReason();

  LeaveApplicationStatus getApplicationStatus();
}

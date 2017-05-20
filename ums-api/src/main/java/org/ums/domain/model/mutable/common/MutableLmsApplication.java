package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.LeaveApplicationStatus;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface MutableLmsApplication extends LmsApplication, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setEmployeeId(String pEmployeeId);

  void setLeaveTypeId(int pTypeId);

  void setAppliedOn(Date pAppliedOn);

  void setFromDate(Date pFromDate);

  void setToDate(Date pToDate);

  void setReason(String pReason);

  void setLeaveApplicationStatus(LeaveApplicationStatus pLeaveApplicationStatus);

}

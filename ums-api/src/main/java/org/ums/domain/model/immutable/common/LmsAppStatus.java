package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApprovalStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsAppStatus extends Serializable, LastModifier, EditType<MutableLmsAppStatus>, Identifier<Long> {

  Long getLmsAppId();

  LmsApplication getLmsApplication();

  Date getActionTakenOn();

  String getActionTakenById();

  Employee getActionTakenBy();

  String getComments();

  LeaveApprovalStatus getActionStatus();

  int getRowNumber();

}

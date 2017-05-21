package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.LeaveApprovalStatus;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface MutableLmsAppStatus extends LmsAppStatus, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setLmsApplicationId(Long pLmsApplicationId);

  void setActionTakenOn(Date pActionTakenOn);

  void setActionTakenById(String pActionTakenBy);

  void setComments(String pComments);

  void setActionStatus(LeaveApprovalStatus pActionStatus);

}

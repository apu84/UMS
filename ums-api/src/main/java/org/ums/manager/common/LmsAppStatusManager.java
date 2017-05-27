package org.ums.manager.common;

import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApprovalStatus;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsAppStatusManager extends ContentManager<LmsAppStatus, MutableLmsAppStatus, Long> {
  List<LmsAppStatus> getAppStatus(Long pApplicationId);

  List<LmsAppStatus> getLmsAppStatusList(String pEmployeeId);

  List<LmsAppStatus> getLmsAppStatusList(LeaveApprovalStatus pLeaveApplicationStatus, Role pRole, User pUser,
      int pageNumber, int pageSize);

  List<LmsAppStatus> getLmsAppStatusList(LeaveApprovalStatus pLeaveApprovalStatus, User pUser, Role pRole);
}

package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.manager.common.LmsAppStatusManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsAppStatusDaoDecorator extends
    ContentDaoDecorator<LmsAppStatus, MutableLmsAppStatus, Long, LmsAppStatusManager> implements LmsAppStatusManager {

  @Override
  public List<LmsAppStatus> getAppStatus(Long pApplicationId) {
    return getManager().getAppStatus(pApplicationId);
  }

  @Override
  public List<LmsAppStatus> getPendingApplications(String pEmployeeId) {
    return getManager().getPendingApplications(pEmployeeId);
  }

  @Override
  public List<LmsAppStatus> getPendingApplications(LeaveApplicationApprovalStatus pLeaveApplicationStatus, Role pRole,
      User pUser, int pageNumber, int pageSize) {
    return getManager().getPendingApplications(pLeaveApplicationStatus, pRole, pUser, pageNumber, pageSize);
  }

  @Override
  public List<LmsAppStatus> getPendingApplications(LeaveApplicationApprovalStatus pLeaveApplicationApprovalStatus,
      User pUser, Role pRole) {
    return getManager().getPendingApplications(pLeaveApplicationApprovalStatus, pUser, pRole);
  }

  @Override
  public LmsAppStatus getLatestStatusOfTheApplication(Long pApplicationId) {
    return getManager().getLatestStatusOfTheApplication(pApplicationId);
  }
}

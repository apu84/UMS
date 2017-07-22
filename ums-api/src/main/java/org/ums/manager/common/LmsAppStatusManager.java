package org.ums.manager.common;

import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.DepartmentType;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.enums.common.LeaveCategories;
import org.ums.manager.ContentManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsAppStatusManager extends ContentManager<LmsAppStatus, MutableLmsAppStatus, Long> {
  List<LmsAppStatus> getAppStatus(Long pApplicationId);

  List<LmsAppStatus> getPendingApplications(String pEmployeeId);

  List<LmsAppStatus> getAllApplications(String pEmployeeId,
      LeaveApplicationApprovalStatus pLeaveApplicationApprovalStatus);

  List<LmsAppStatus> getAllApplications(String pEmployeeId,
      LeaveApplicationApprovalStatus pLeaveApplicationApprovalStatus, int pageNumber, int pageSize);

  List<LmsAppStatus> getPendingApplications(LeaveApplicationApprovalStatus pLeaveApplicationStatus, Role pRole,
      User pUser, int pageNumber, int pageSize);

  List<LmsAppStatus> getPendingApplications(LeaveApplicationApprovalStatus pLeaveApplicationApprovalStatus, User pUser,
      Role pRole);

  LmsAppStatus getLatestStatusOfTheApplication(Long pApplicationId);

  List<LmsAppStatus> getApplicationsApprovedOfTheDay(DepartmentType pDepartmentType, int pageNumber, int totalSize);

  List<LmsAppStatus> getApplicationsApprovedOfTheDay(DepartmentType pDepartmentType, LeaveCategories pLeaveCategories,
      int pageNumber, int totalSize);

  List<LmsAppStatus> getApplicationsApprovedOfTheDay(DepartmentType pDepartmentType);

  List<LmsAppStatus> getApplicationsApprovedOfTheDay(DepartmentType pDepartmentType, LeaveCategories pLeaveCategories);

}

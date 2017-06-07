package org.ums.services.leave.management;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.enums.common.LeaveApplicationStatus;
import org.ums.enums.common.LeaveCategories;
import org.ums.enums.common.RoleType;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.persistent.model.common.PersistentLmsAppStatus;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;
import org.ums.usermanagement.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-May-17.
 */
@Component
public class LeaveManagementService {

  private Logger mLogger = LoggerFactory.getLogger(LeaveManagementService.class);

  @Autowired
  private NotificationGenerator mNotificationGenerator;

  @Autowired
  LmsApplicationManager mLmsApplicationManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  public void setNotification(String userId, String message) {
    Notifier notifier = new Notifier() {
      @Override
      public List<String> consumers() {
        List<String> users = new ArrayList<>();
        users.add(userId);
        return users;
      }

      @Override
      public String producer() {
        return SecurityUtils.getSubject().getPrincipal().toString();
      }

      @Override
      public String notificationType() {
        return new StringBuilder(Notification.Type.LEAVE_APPLICATION.getValue()).toString();
      }

      @Override
      public String payload() {
        try {
          return message;
        } catch (Exception e) {
          mLogger.error("Exception while looking for user: ", e);
        }
        return null;
      }
    };

    try {
      mNotificationGenerator.notify(notifier);
    } catch (Exception e) {
      mLogger.error("Failed to generate notification", e);
    }
  }

  public void setApplicationStatus(PersistentLmsAppStatus pLmsAppStatus, User pUser, List<Integer> pAdditionalRoleIds,
                                   int pApprovalStatus, LmsAppStatus pLatestStatusOfTheApplication) {
    if ((pUser.getPrimaryRole().getId() == RoleType.VC.getId() || pAdditionalRoleIds.contains(RoleType.VC.getId()))
        && pLatestStatusOfTheApplication.getActionStatus().getId() == LeaveApplicationApprovalStatus.WAITING_FOR_VC_APPROVAL
        .getId()
        ) {                                                         //&& pLmsAppStatus.getLmsApplication().getLmsType().getId() != LeaveCategories.CASUAL_LEAVE_ON_FULL_PAY.getId()
      assignActionOfVC(pLmsAppStatus, pApprovalStatus);
    } else if ((pUser.getPrimaryRole().getId() == RoleType.REGISTRAR.getId() || pAdditionalRoleIds
        .contains(RoleType.REGISTRAR.getId()))
        && pLatestStatusOfTheApplication.getActionStatus().equals(
        LeaveApplicationApprovalStatus.WAITING_FOR_REGISTRARS_APPROVAL)) {
      assignActionOfRegistrar(pLmsAppStatus, pUser, pApprovalStatus);
    } else {
      assignActionOfHead(pLmsAppStatus, pApprovalStatus);
    }
  }

  private void assignActionOfHead(PersistentLmsAppStatus pLmsAppStatus, int pApprovalStatus) {
    if (pLmsAppStatus.getLmsApplication().getLmsType().getId() == LeaveCategories.CASUAL_LEAVE_ON_FULL_PAY.getId()) {
      if (pApprovalStatus == LeaveApplicationStatus.ACCEPTED.getId()) {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.APPLICATION_APPROVED);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.APPLICATION_APPROVED);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is approved by Dept. Head");
      } else {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.REJECTED_BY_DEPT_HEAD);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.REJECTED_BY_DEPT_HEAD);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is rejected by Dept. Head");

      }
    } else {
      if (pApprovalStatus == LeaveApplicationStatus.ACCEPTED.getId()) {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.WAITING_FOR_REGISTRARS_APPROVAL);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.WAITING_FOR_REGISTRARS_APPROVAL);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is waiting for Registrar's approval");

      } else {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.REJECTED_BY_DEPT_HEAD);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.REJECTED_BY_DEPT_HEAD);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is rejected by Dept. Head");
      }
    }
  }

  private void assignActionOfRegistrar(PersistentLmsAppStatus pLmsAppStatus, User pUser, int pApprovalStatus) {
    if (pLmsAppStatus.getLmsApplication().getLmsType().getId() == LeaveCategories.CASUAL_LEAVE_ON_FULL_PAY.getId()
        && pLmsAppStatus.getLmsApplication().getEmployee().getDepartment()
        .equals(mEmployeeManager.get(pUser.getEmployeeId()).getDepartment())) {
      if (pApprovalStatus == LeaveApplicationStatus.ACCEPTED.getId()) {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.APPLICATION_APPROVED);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.APPLICATION_APPROVED);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is approved by Registrar");
      } else {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.REJECTED_BY_REGISTRAR);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.REJECTED_BY_REGISTRAR);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is rejected by Registrar");
      }
    } else {
      if (pApprovalStatus == LeaveApplicationStatus.ACCEPTED.getId()) {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.WAITING_FOR_VC_APPROVAL);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.WAITING_FOR_VC_APPROVAL);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is waiting for VC's approval");
      } else {
        pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.REJECTED_BY_REGISTRAR);
        mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
            LeaveApplicationApprovalStatus.REJECTED_BY_REGISTRAR);
        setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
            "Your leave application is rejected by Registrar");
      }
    }
  }

  private void assignActionOfVC(PersistentLmsAppStatus pLmsAppStatus, int pApprovalStatus) {
    if (pApprovalStatus == LeaveApplicationStatus.ACCEPTED.getId()) {
      pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.APPLICATION_APPROVED);
      mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
          LeaveApplicationApprovalStatus.APPLICATION_APPROVED);
      setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
          "Your leave application is approved by VC");
    } else {
      pLmsAppStatus.setActionStatus(LeaveApplicationApprovalStatus.REJECTED_BY_VC);
      mLmsApplicationManager.updateApplicationStatus(pLmsAppStatus.getLmsApplication().getId(),
          LeaveApplicationApprovalStatus.REJECTED_BY_VC);
      setNotification(mEmployeeManager.get(pLmsAppStatus.getLmsApplication().getEmployeeId()).getShortName(),
          "Your leave application is rejected by VC");
    }
  }

}

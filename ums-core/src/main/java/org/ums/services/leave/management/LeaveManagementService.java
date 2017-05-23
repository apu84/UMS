package org.ums.services.leave.management;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Notification;
import org.ums.manager.NotificationManager;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-May-17.
 */
@Component
public class LeaveManagementService {

  private Logger mLogger = LoggerFactory.getLogger(LeaveManagementService.class);

  @Autowired
  private NotificationManager mNotificationManager;

  @Autowired
  private NotificationGenerator mNotificationGenerator;

  public void setNotification(String userId, Employee sender) {
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
          return "Leave Application from employee: " + sender.getEmployeeName() + " of department: "
              + sender.getDepartment().getShortName() + " is waiting for your approval.";
        } catch(Exception e) {
          mLogger.error("Exception while looking for user: ", e);
        }
        return null;
      }
    };

    try {
      mNotificationGenerator.notify(notifier);
    } catch(Exception e) {
      mLogger.error("Failed to generate notification", e);
    }
  }

}

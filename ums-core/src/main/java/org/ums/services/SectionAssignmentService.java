package org.ums.services;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Notification;
import org.ums.manager.EmployeeManager;
import org.ums.manager.NotificationManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 5/6/2018.
 */
@Component
public class SectionAssignmentService {
  private Logger mLogger = LoggerFactory.getLogger(SectionAssignmentService.class);

  @Autowired
  UserManager mUserManager;
  @Autowired
  EmployeeManager mEmployeeManager;
  @Autowired
  private NotificationGenerator mNotificationGenerator;

  public void setNotification(String pStudentId, String pTheorySection, String pSessionalSection) {
    Notifier notifier = new Notifier() {

      @Override
      public List<String> consumers() {
        List<String> users = new ArrayList<>();
        users.add(pStudentId);
        return users;
      }

      @Override
      public String producer() {
        // String userId = SecurityUtils.getSubject().getPrincipal().toString();
        return "Admin Officer";
      }

      @Override
      public String notificationType() {
        return new StringBuilder(Notification.Type.SECTION_ASSIGNMENT.getValue()).toString();
      }

      @Override
      public String payload() {
        try {
          return "Your Section has been updated.You New Theory Section: " + pTheorySection + "  and"
              + " Sessional Section: " + pSessionalSection;
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

package org.ums.services;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Notification;
import org.ums.manager.NotificationManager;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApprovePublicationService {

  private Logger mLogger = LoggerFactory.getLogger(ApprovePublicationService.class);

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
        return new StringBuilder(Notification.Type.APPROVE_PUBLICATION.getValue()).toString();
      }

      @Override
      public String payload() {
        try {
          return sender.getEmployeeName() + "'s publication is waiting for your approval";
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

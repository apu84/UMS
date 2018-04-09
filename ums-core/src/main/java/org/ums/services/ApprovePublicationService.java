package org.ums.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.configuration.FirebaseConfig;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Notification;
import org.ums.manager.NotificationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class ApprovePublicationService {

  private Logger mLogger = LoggerFactory.getLogger(ApprovePublicationService.class);

  @Autowired
  private NotificationManager mNotificationManager;

  @Autowired
  private NotificationGenerator mNotificationGenerator;

  @Autowired
  private FirebaseConfig mFirebaseConfig;

  public void setNotification(String userId, Employee sender) throws IOException, ExecutionException,
      InterruptedException {

    String registrationToken = "AAAAEY8u0Hc:APA91bHPqGk2ZzrRdi9t7OKAsV-ZhSM8wZpxO5qn8r2ho46Icx0YzhGT8yPwCfGD47nJQJbSFjiaWSbviGV9T-BNF7Q7tJATg6oGeZ296oOheZRJlhl8-KoetBpiGCZ6svkVTHf_RGiW";

    Message message =
        Message.builder().putData("score", "850").putData("time", "2:45").setToken(registrationToken).build();

    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    System.out.println("Successfully sent message: " + response);

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
          return sender.getPersonalInformation().getFullName() + "'s publication is waiting for your approval";
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

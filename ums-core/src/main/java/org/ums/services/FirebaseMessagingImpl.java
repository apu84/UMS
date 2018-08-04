package org.ums.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.FCMTokenManager;
import org.ums.manager.NotificationManager;
import org.ums.persistent.model.PersistentNotification;

@Component
public class FirebaseMessagingImpl {

  private Logger mLogger = LoggerFactory.getLogger(FirebaseMessagingImpl.class);

  @Autowired
  FCMTokenManager mFCMTokenManager;

  @Autowired
  NotificationManager mNotificationManager;

  @Transactional
  public void send(String pProducerId, String pConsumerId, String pTitle, String pBody, boolean saveNotification) {
    Long notificationId = null;
    if(saveNotification) {
      notificationId = saveNotification(pProducerId, pConsumerId, pTitle, pBody);
    }
    if(mFCMTokenManager.exists(pConsumerId)) {
      FCMToken fcmToken = mFCMTokenManager.get(pConsumerId);
      try {
        Notification notification = new Notification(pTitle, pBody);
        Message message = Message.builder().setNotification(notification).setToken(fcmToken.getToken()).build();
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        mLogger.info("Sent message: " + response);
        if(notificationId != null) {
          updateNotification((MutableNotification) mNotificationManager.get(notificationId));
        }
      } catch(Exception e) {
        mLogger.error("Error in sending message: " + e.getMessage());
        mLogger.error("" + e);
      }
    }
    else {
      mLogger.info("Message not sent to user: " + pConsumerId);
      mLogger.info("This user [" + pConsumerId + "] does not exists in [FCM_TOKEN] table");
    }
  }

  private Long saveNotification(String pProducerId, String pConsumerId, String pTitle, String pBody) {
    MutableNotification notification = new PersistentNotification();
    notification.setProducerId(pProducerId);
    notification.setConsumerId(pConsumerId);
    notification.setNotificationType(pTitle);
    notification.setPayload(pBody);
    return notification.create();
  }

  private void updateNotification(MutableNotification pMutable) {
    pMutable.update();
  }
}

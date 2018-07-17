package org.ums.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.FCMTokenManager;
import org.ums.persistent.model.PersistentFCMToken;

import java.util.Date;

@Component
public class FirebaseMessagingImpl {

  private Logger mLogger = LoggerFactory.getLogger(FirebaseMessagingImpl.class);

  @Autowired
  FCMTokenManager mFCMTokenManager;

    public void send(String receiverId, String title, String body) {

    if(mFCMTokenManager.exists(receiverId)) {
      FCMToken fcmToken = mFCMTokenManager.get(receiverId);

      if(fcmToken.getToken() == null) {
        if(fcmToken.getDeleteOn() == null) {
          updateConsumer(fcmToken);
        }
      }
      else {
          try {
              Notification notification = new Notification(title, body);
              Message message = Message.builder().setNotification(notification).setToken(fcmToken.getToken()).build();
              String response = FirebaseMessaging.getInstance().sendAsync(message).get();
              mLogger.info("Sent message: " + response);
          } catch (Exception e) {
              mLogger.error("Error in sending message: " + e.getMessage());
              mLogger.error("" + e);
          }
      }
    }
    else {
      createConsumer(receiverId);
    }
  }

  private void updateConsumer(FCMToken fcmToken) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    mutableFCMToken.setId(fcmToken.getId());
    mutableFCMToken.setToken(null);
    mutableFCMToken.setRefreshedOn(new Date(fcmToken.getRefreshedOn().getTime()));
    mutableFCMToken.setDeletedOn(new Date());
    mFCMTokenManager.update(mutableFCMToken);
  }

  private void createConsumer(String consumerId) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    mutableFCMToken.setId(consumerId);
    mutableFCMToken.setToken(null);
    mutableFCMToken.setRefreshedOn(null);
    mutableFCMToken.setDeletedOn(new Date());
    mFCMTokenManager.create(mutableFCMToken);
  }
}

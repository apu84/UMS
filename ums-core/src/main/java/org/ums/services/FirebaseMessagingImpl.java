package org.ums.services;

import com.google.firebase.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.FCMTokenManager;
import org.ums.persistent.model.PersistentFCMToken;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Component
public class FirebaseMessagingImpl implements FirebaseMessaging {

  private Logger mLogger = LoggerFactory.getLogger(ApprovePublicationService.class);

  @Autowired
  FCMTokenManager mFCMTokenManager;

  @Override
  public void send(String consumerId, String messageKey, String messageValue) throws InterruptedException,
      ExecutionException {

    if(mFCMTokenManager.exists(consumerId)) {
      FCMToken fcmToken = mFCMTokenManager.get(consumerId);

      if(fcmToken.getFCMToken() == null) {
        if(fcmToken.getTokenDeleteOn() == null) {
          updateConsumer(fcmToken);
        }
      }
      else {
        Message message = Message.builder().putData(messageKey, messageValue).setToken(fcmToken.getFCMToken()).build();
        String response = com.google.firebase.messaging.FirebaseMessaging.getInstance().sendAsync(message).get();
        mLogger.info("Sent message: " + response);
      }
    }
    else {
      createConsumer(consumerId);
    }
  }

  private void updateConsumer(FCMToken fcmToken) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    mutableFCMToken.setId(fcmToken.getId());
    mutableFCMToken.setFCMToken(null);
    mutableFCMToken.setTokenLastRefreshedOn(new Date(fcmToken.getTokenLastRefreshedOn().getTime()));
    mutableFCMToken.setTokenDeletedOn(new Date());
    mFCMTokenManager.update(mutableFCMToken);
  }

  private void createConsumer(String consumerId) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    mutableFCMToken.setId(consumerId);
    mutableFCMToken.setFCMToken(null);
    mutableFCMToken.setTokenLastRefreshedOn(null);
    mutableFCMToken.setTokenDeletedOn(new Date());
    mFCMTokenManager.create(mutableFCMToken);
  }
}
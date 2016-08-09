package org.ums.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.NotificationManager;
import org.ums.persistent.model.PersistentNotification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationGenerator {
  @Autowired
  @Qualifier("notificationManager")
  NotificationManager mNotificationManager;

  @Async
  public void notify(Notifier pNotifier) throws Exception {
    List<User> consumers = pNotifier.consumers();
    User producer = pNotifier.producer();
    String notificationType = pNotifier.notificationType();
    String payload = pNotifier.payload();

    List<MutableNotification> notificationList = new ArrayList<>();

    for (User consumer: consumers) {
      MutableNotification notification = new PersistentNotification();
      notification.setProducer(producer);
      notification.setConsumer(consumer);
      notification.setPayload(payload);
      notification.setNotificationType(notificationType);
      notificationList.add(notification);
    }

    mNotificationManager.create(notificationList);
  }
}

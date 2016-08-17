package org.ums.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
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
public class NotificationGeneratorImpl implements NotificationGenerator{
  @Autowired
  @Qualifier("notificationManager")
  NotificationManager mNotificationManager;

  @Async
  public void notify(Notifier pNotifier) throws Exception {
    List<String> consumers = pNotifier.consumers();
    String producer = pNotifier.producer();
    String notificationType = pNotifier.notificationType();
    String payload = pNotifier.payload();

    List<MutableNotification> notificationList = new ArrayList<>();

    for (String consumer: consumers) {
      MutableNotification notification = new PersistentNotification();
      notification.setProducerId(producer);
      notification.setConsumerId(consumer);
      notification.setPayload(payload);
      notification.setNotificationType(notificationType);
      notificationList.add(notification);
    }

    mNotificationManager.create(notificationList);
  }
}

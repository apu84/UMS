package org.ums.services;

import org.springframework.scheduling.annotation.Async;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.NotificationManager;
import org.ums.persistent.model.PersistentNotification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotificationGeneratorImpl implements NotificationGenerator{
  private NotificationManager mNotificationManager;
  private DateFormat dateFormat;

  public NotificationGeneratorImpl(NotificationManager pNotificationManager) {
    mNotificationManager = pNotificationManager;
    dateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");
  }

  @Async
  public void notify(Notifier pNotifier) throws Exception {
    List<String> consumers = pNotifier.consumers();
    String producer = pNotifier.producer();
    String notificationType = pNotifier.notificationType();
    String payload = pNotifier.payload();

    List<MutableNotification> notificationList = new ArrayList<>();

    for (String consumer: consumers) {
      MutableNotification notification = new PersistentNotification();
      notification.setId(UUID.randomUUID().toString());
      notification.setProducerId(producer);
      notification.setConsumerId(consumer);
      notification.setPayload(payload);
      notification.setNotificationType(notificationType);
      notification.setProducedOn(new Date());
      notification.setLastModified(dateFormat.format(new Date()));
      notificationList.add(notification);
    }

    mNotificationManager.create(notificationList);
  }
}

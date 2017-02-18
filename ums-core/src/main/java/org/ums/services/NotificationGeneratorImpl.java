package org.ums.services;

import org.springframework.scheduling.annotation.Async;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.generator.IdGenerator;
import org.ums.manager.NotificationManager;
import org.ums.persistent.model.PersistentNotification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotificationGeneratorImpl implements NotificationGenerator {
  private NotificationManager mNotificationManager;
  private DateFormat dateFormat;
  private IdGenerator mIdGenerator;

  public NotificationGeneratorImpl(NotificationManager pNotificationManager,
      IdGenerator pIdeIdGenerator) {
    mNotificationManager = pNotificationManager;
    dateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");
    mIdGenerator = pIdeIdGenerator;
  }

  @Async
  public void notify(Notifier pNotifier) {
    List<String> consumers = pNotifier.consumers();
    String producer = pNotifier.producer();
    String notificationType = pNotifier.notificationType();
    String payload = pNotifier.payload();

    List<MutableNotification> notificationList = new ArrayList<>();

    for(String consumer : consumers) {
      MutableNotification notification = new PersistentNotification();
      notification.setId(mIdGenerator.getNumericId());
      notification.setProducerId(producer);
      notification.setConsumerId(consumer);
      notification.setPayload(payload);
      notification.setNotificationType(notificationType);
      notification.setProducedOn(new Date());
      notification.setConsumedOn(new Date());
      notification.setLastModified(dateFormat.format(new Date()));
      notificationList.add(notification);
    }

    mNotificationManager.create(notificationList);
  }
}

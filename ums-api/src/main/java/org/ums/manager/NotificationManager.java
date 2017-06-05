package org.ums.manager;

import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;

import java.util.List;

public interface NotificationManager extends ContentManager<Notification, MutableNotification, Long> {
  List<Notification> getNotifications(String pConsumerId, String pNotificationType);

  List<Notification> getNotifications(String pConsumerId, Integer pNumOfLatestNotification);
}

package org.ums.manager;


import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableNotification;

import java.util.List;

public interface NotificationManager extends ContentManager<Notification, MutableNotification, Integer> {
  List<Notification> consume(User pConsumer, String pNotificationType);
}

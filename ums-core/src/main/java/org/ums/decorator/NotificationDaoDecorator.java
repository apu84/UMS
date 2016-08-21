package org.ums.decorator;

import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.NotificationManager;

import java.util.List;


public class NotificationDaoDecorator extends ContentDaoDecorator<Notification, MutableNotification, String, NotificationManager>
    implements NotificationManager {
  @Override
  public List<Notification> consume(String pConsumerId, String pNotificationType) {
    return getManager().consume(pConsumerId, pNotificationType);
  }
}

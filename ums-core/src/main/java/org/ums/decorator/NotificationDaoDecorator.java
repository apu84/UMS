package org.ums.decorator;

import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.NotificationManager;

import java.util.List;

public class NotificationDaoDecorator extends
    ContentDaoDecorator<Notification, MutableNotification, Long, NotificationManager> implements NotificationManager {
  @Override
  public List<Notification> getNotifications(String pConsumerId, String pNotificationType) {
    return getManager().getNotifications(pConsumerId, pNotificationType);
  }

  @Override
  public List<Notification> getNotifications(String pConsumerId, Integer pNumOfLatestNotification) {
    return getManager().getNotifications(pConsumerId, pNumOfLatestNotification);
  }

  @Override
  public List<Notification> getNotifications(String consumerId) {
    return getManager().getNotifications(consumerId);
  }
}

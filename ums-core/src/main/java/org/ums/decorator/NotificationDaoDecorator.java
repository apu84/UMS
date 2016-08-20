package org.ums.decorator;

import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.NotificationManager;

import java.util.List;


public class NotificationDaoDecorator extends ContentDaoDecorator<Notification, MutableNotification, String, NotificationManager>
    implements NotificationManager {
  @Override
  public List<Notification> consume(User pConsumer, String pNotificationType) {
    return getManager().consume(pConsumer, pNotificationType);
  }
}

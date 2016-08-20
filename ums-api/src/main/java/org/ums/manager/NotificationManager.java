package org.ums.manager;


import org.omg.CORBA.StringHolder;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableNotification;

import java.util.List;

public interface NotificationManager extends ContentManager<Notification, MutableNotification, String> {
  List<Notification> consume(User pConsumer, String pNotificationType);
}

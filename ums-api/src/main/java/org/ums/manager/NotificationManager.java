package org.ums.manager;


import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableNotification;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NotificationManager extends ContentManager<Notification, MutableNotification, String> {
  List<Notification> consume(String pConsumerId, String pNotificationType);
}

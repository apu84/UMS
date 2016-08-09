package org.ums.services;

import org.ums.domain.model.immutable.User;

import java.util.List;

public interface Notifier {
  List<User> consumers() throws Exception;

  User producer() throws Exception;

  String notificationType();

  String payload();
}

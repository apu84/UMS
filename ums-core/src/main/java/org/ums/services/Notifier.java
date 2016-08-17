package org.ums.services;

import org.ums.domain.model.immutable.User;

import java.util.List;

public interface Notifier {
  List<String> consumers() throws Exception;

  String producer() throws Exception;

  String notificationType();

  String payload();
}

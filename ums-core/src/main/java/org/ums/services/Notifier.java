package org.ums.services;

import org.ums.domain.model.immutable.User;

import java.util.List;

public interface Notifier {
  List<String> consumers();

  String producer();

  String notificationType();

  String payload();
}

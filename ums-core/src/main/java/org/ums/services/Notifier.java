package org.ums.services;

import java.util.List;

public interface Notifier {
  List<String> consumers();

  String producer();

  String notificationType();

  String payload();
}

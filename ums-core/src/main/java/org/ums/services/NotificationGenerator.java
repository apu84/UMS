package org.ums.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationGenerator {
  @Async
  public void notify(Notifier pNotifier) {
    
  }
}

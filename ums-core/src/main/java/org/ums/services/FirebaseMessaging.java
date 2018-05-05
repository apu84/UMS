package org.ums.services;

import java.util.concurrent.ExecutionException;

public interface FirebaseMessaging {

  void send(String consumerId, String messageKey, String messageBody) throws InterruptedException, ExecutionException;
}

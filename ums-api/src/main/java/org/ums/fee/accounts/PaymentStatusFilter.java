package org.ums.fee.accounts;

import java.util.Date;

public interface PaymentStatusFilter {
  Date getReceivedStart();

  Date getReceivedEnd();

  String getTransactionId();

  String getAccount();

  PaymentStatus.PaymentMethod getPaymentMethod();

  String isPaymentCompleted();
}

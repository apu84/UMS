package org.ums.fee.accounts;

import org.ums.manager.ContentManager;

import java.util.List;

public interface PaymentStatusManager extends ContentManager<PaymentStatus, MutablePaymentStatus, Long> {
  List<PaymentStatus> getByTransactionId(String pTransactionId);
}

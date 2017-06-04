package org.ums.fee.accounts;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class PaymentStatusDaoDecorator extends
    ContentDaoDecorator<PaymentStatus, MutablePaymentStatus, Long, PaymentStatusManager> implements
    PaymentStatusManager {
  @Override
  public List<PaymentStatus> getByTransactionId(String pTransactionId) {
    return getManager().getByTransactionId(pTransactionId);
  }
}

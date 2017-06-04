package org.ums.fee.accounts;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class PaymentStatusDaoDecorator extends
    ContentDaoDecorator<PaymentStatus, MutablePaymentStatus, Long, PaymentStatusManager> implements
    PaymentStatusManager {
  @Override
  public List<PaymentStatus> getByTransactionId(String pTransactionId) {
    return getManager().getByTransactionId(pTransactionId);
  }

  @Override
  public List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber) {
    return getManager().paginatedList(itemsPerPage, pageNumber);
  }

  @Override
  public List<PaymentStatus> paginatedList(PaymentStatusFilter filter, int itemsPerPage, int pageNumber) {
    return getManager().paginatedList(filter, itemsPerPage, pageNumber);
  }
}

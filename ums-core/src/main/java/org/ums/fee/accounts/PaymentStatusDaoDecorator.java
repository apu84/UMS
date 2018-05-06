package org.ums.fee.accounts;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.filter.ListFilter;

public class PaymentStatusDaoDecorator extends
    ContentDaoDecorator<PaymentStatus, MutablePaymentStatus, Long, PaymentStatusManager> implements
    PaymentStatusManager {
  @Override
  public List<PaymentStatus> getByTransactionId(String pTransactionId) {
    return getManager().getByTransactionId(pTransactionId);
  }

  @Override
  public List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber, Long pBranchId) {
    return getManager().paginatedList(itemsPerPage, pageNumber, pBranchId);
  }

  @Override
  public List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters, Long pBranchId) {
    return getManager().paginatedList(itemsPerPage, pageNumber, pFilters, pBranchId);
  }
}

package org.ums.fee.accounts;

import java.util.List;

import org.ums.manager.ContentManager;

public interface PaymentStatusManager extends ContentManager<PaymentStatus, MutablePaymentStatus, Long> {
  List<PaymentStatus> getByTransactionId(String pTransactionId);

  List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber);

  List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber, List<FilterCriteria> pFilterCriteria);
}

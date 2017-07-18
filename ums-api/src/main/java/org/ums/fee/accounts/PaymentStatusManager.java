package org.ums.fee.accounts;

import java.util.List;

import org.ums.filter.ListFilter;
import org.ums.manager.ContentManager;

public interface PaymentStatusManager extends ContentManager<PaymentStatus, MutablePaymentStatus, Long> {
  List<PaymentStatus> getByTransactionId(String pTransactionId);

  List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber);

  List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters);

  enum FilterCriteria {
    RECEIVED_START,
    RECEIVED_END,
    ACCOUNT,
    METHOD_OF_PAYMENT,
    TRANSACTION_ID,
    PAYMENT_STATUS
  }
}

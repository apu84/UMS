package org.ums.fee.accounts;

public interface FilterCriteria {
  Criteria getCriteria();

  Object getValue();

  enum Criteria {
    RECEIVED_START,
    RECEIVED_END,
    ACCOUNT,
    METHOD_OF_PAYMENT,
    TRANSACTION_ID,
    PAYMENT_COMPLETED
  }
}

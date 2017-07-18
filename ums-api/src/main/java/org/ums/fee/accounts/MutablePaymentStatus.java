package org.ums.fee.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;
import java.util.Date;

public interface MutablePaymentStatus extends PaymentStatus, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setAccount(String pAccount);

  void setTransactionId(String pTransactionId);

  void setMethodOfPayment(PaymentMethod pMethodOfPayment);

  void setStatus(Status pStatus);

  void setReceivedOn(Date pReceivedOn);

  void setCompletedOn(Date pCompletedOn);

  void setAmount(BigDecimal pAmount);

  void setPaymentDetails(String pPaymentDetails);

  void setReceiptNo(String pReceiptNo);
}

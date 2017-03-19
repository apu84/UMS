package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.enums.PaymentMode;
import org.ums.enums.PaymentType;

/**
 * Created by Monjur-E-Morshed on 23-Jan-17.
 */
public interface MutablePaymentInfo extends PaymentInfo, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setReferenceId(final String pReceiptId);

  void setSemesterId(final int pSemesterId);

  void setPaymentType(final PaymentType pPaymentType);

  // todo integrate with payment system
  void setAmount(final int pAmount);

  void setPaymentDate(final String pPaymentDate);

  void setPaymentMode(final PaymentMode pPaymentMode);
}

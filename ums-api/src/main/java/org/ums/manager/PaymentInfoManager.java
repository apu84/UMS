package org.ums.manager;

import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.mutable.MutablePaymentInfo;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public interface PaymentInfoManager extends ContentManager<PaymentInfo, MutablePaymentInfo, Integer> {
  List<PaymentInfo> getPaymentInfo(final String pReferenceId, final int pSemesterId, String pQuota);
}

package org.ums.decorator;

import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.manager.PaymentInfoManager;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public class PaymentInfoDaoDecorator
    extends ContentDaoDecorator<PaymentInfo, MutablePaymentInfo, Integer, PaymentInfoManager>
    implements PaymentInfoManager {
}

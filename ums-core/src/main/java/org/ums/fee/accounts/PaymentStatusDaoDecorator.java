package org.ums.fee.accounts;

import org.ums.decorator.ContentDaoDecorator;

public class PaymentStatusDaoDecorator extends
    ContentDaoDecorator<PaymentStatus, MutablePaymentStatus, Long, PaymentStatusManager> implements
    PaymentStatusManager {
}

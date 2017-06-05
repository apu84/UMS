package org.ums.fee.accounts;

import org.ums.decorator.ContentDaoDecorator;

public class PaymentAccountsMappingDaoDecorator extends
    ContentDaoDecorator<PaymentAccountsMapping, MutablePaymentAccountsMapping, Long, PaymentAccountsMappingManager>
    implements PaymentAccountsMappingManager {
}

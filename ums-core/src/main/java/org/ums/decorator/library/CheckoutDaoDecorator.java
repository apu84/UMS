package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Checkout;
import org.ums.domain.model.mutable.library.MutableCheckout;
import org.ums.manager.library.CheckoutManager;

public class CheckoutDaoDecorator extends ContentDaoDecorator<Checkout, MutableCheckout, Long, CheckoutManager>
    implements CheckoutManager {
}

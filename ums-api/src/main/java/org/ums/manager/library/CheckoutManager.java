package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Checkout;
import org.ums.domain.model.mutable.library.MutableCheckout;
import org.ums.manager.ContentManager;

public interface CheckoutManager extends ContentManager<Checkout, MutableCheckout, Long> {
}

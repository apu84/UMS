package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface CurrencyManager
    extends
    ContentManager<Currency, MutableCurrency, Long> {
}
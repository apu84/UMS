package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.manager.accounts.CurrencyManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class CurrencyDaoDecorator
    extends
    ContentDaoDecorator<Currency, MutableCurrency, Long, CurrencyManager>
    implements
    CurrencyManager {
}
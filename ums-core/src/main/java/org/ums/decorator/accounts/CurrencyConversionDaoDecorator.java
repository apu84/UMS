package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;
import org.ums.manager.accounts.CurrencyConversionManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class CurrencyConversionDaoDecorator extends
    ContentDaoDecorator<CurrencyConversion, MutableCurrencyConversion, Long, CurrencyConversionManager> implements
    CurrencyConversionManager {
}

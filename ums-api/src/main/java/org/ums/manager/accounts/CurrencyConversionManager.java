package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface CurrencyConversionManager extends ContentManager<CurrencyConversion, MutableCurrencyConversion, Long> {
  List<CurrencyConversion> getAll(Company pCompany);
}

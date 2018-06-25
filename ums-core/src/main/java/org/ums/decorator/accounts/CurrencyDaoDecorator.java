package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.manager.accounts.CurrencyManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class CurrencyDaoDecorator extends ContentDaoDecorator<Currency, MutableCurrency, Long, CurrencyManager>
    implements CurrencyManager {
  @Override
  public Currency getBaseCurrency(Company pCompany) {
    return getManager().getBaseCurrency(pCompany);
  }

  @Override
  public List<Currency> getAll(Company pCompany) {
    return getManager().getAll(pCompany);
  }
}

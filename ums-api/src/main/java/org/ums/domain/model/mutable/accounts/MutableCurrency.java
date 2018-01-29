package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface MutableCurrency extends Currency, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setCurrencyCode(Integer pCurrencyCode);

  void setCurrencyDescription(String pCurrencyDescription);

  void setCurrencyFlag(CurrencyFlag pCurrencyFlag);

  void setNotation(String pNotation);

  void setDefaultCompany(String pDefaultCompany);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}

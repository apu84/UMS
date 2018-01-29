package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface MutableCurrencyConversion
    extends
    CurrencyConversion,
    Editable<Long>,
    MutableIdentifier<Long>,
    MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setDefaultCompany(Company pCompany);

  void setDefaultCompanyId(String pCompanyId);

  void setCurrency(Currency pCurrency);

  void setCurrencyId(Long pCurrencyId);

  void setConversionFactor(BigDecimal pConversionFactor);

  void setReverseConversionFactor(BigDecimal pReverseConversionFactor);

  void setBaseConversionFactor(BigDecimal pBaseConversionFactor);

  void setReverseBaseConversionFactor(BigDecimal pReverseBaseConversionFactor);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface CurrencyConversion
    extends
    Serializable,
    EditType<MutableCurrencyConversion>,
    LastModifier,
    Identifier<Long> {

  Company getCompany();

  String getCompanyId();

  Currency getCurrency();

  Long getCurrencyId();

  BigDecimal getConversionFactor();

  BigDecimal getReverseConversionFactor();

  BigDecimal getBaseConversionFactor();

  BigDecimal getReverseBaseConversionFactor();

  Company getDefaultCompany();

  String getDefaultCompanyId();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();
}
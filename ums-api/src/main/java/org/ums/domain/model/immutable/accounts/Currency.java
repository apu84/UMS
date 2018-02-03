package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface Currency extends Serializable, EditType<MutableCurrency>, LastModifier, Identifier<Long> {

  Company getCompany();

  String getCompanyId();

  Integer getCurrencyCode();

  String getCurrencyDescription();

  CurrencyFlag getCurrencyFlag();

  String getNotation();

  String getDefaultCompany();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();
}

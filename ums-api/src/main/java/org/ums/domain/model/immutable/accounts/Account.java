package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableAccount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface Account extends Serializable, LastModifier, EditType<MutableAccount>, Identifier<Long> {
  Integer getRowNumber();

  Long getAccountCode();

  String getAccountName();

  String getAccGroupCode();

  Boolean getReserved();

  BigDecimal getTaxLimit();

  String getTaxCode();

  String getDefaultComp();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();

  Company getCompany();

  String getCompanyId();
}

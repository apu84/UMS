package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface MutableAccount extends Account, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setAccountCode(String pAccountCode);

  void setAccountName(String pAccountName);

  void setAccGroupCode(String pAccGroupCode);

  void setReserved(Boolean pReserved);

  void setTaxLimit(BigDecimal pTaxLimit);

  void setTaxCode(String pTaxCode);

  void setDefaultComp(String pDefaultComp);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}

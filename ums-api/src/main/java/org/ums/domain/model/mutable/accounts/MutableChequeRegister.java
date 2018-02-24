package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
public interface MutableChequeRegister extends ChequeRegister, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setAccountTransaction(AccountTransaction pAccountTransaction);

  void setAccountTransactionId(Long pAccountTransactionId);

  void setCheckNo(String pCheckNo);

  void setChequeDate(Date pChequeDate);

  void setStatus(String pStatus);

  void setRealizationDate(Date pRealizationDate);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModificationDate(Date pModificationDate);

  void setModifiedBy(String pModifiedBy);
}

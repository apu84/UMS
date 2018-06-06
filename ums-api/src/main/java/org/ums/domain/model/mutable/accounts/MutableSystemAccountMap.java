package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.account.balance.AccountType;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public interface MutableSystemAccountMap extends SystemAccountMap, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setAccountType(AccountType pAccountType);

  void setAccount(Account pAccount);

  void setAccountId(Long pAccountId);

  void setCompany(Company pCompany);

  void setCompanyId(String pCompanyId);

  void setModifiedBy(String pModifiedBy);

  void setModifierName(String pModifierName);

  void setModifiedDate(Date pModifiedDate);
}

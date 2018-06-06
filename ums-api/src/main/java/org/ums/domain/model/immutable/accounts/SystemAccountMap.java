package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.enums.accounts.definitions.account.balance.AccountType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public interface SystemAccountMap extends Serializable, EditType<MutableSystemAccountMap>, LastModifier,
    Identifier<Long> {

  AccountType getAccountType();

  Account getAccount();

  Long getAccountId();

  Company getCompany();

  String getCompanyId();

  String getModifiedBy();

  String getModifierName();

  Date getModifiedDate();
}
